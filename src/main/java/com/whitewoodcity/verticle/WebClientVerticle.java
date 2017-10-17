package com.whitewoodcity.verticle;

import com.whitewoodcity.util.StringUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

import java.util.Map;
import java.util.UUID;

public class WebClientVerticle extends AbstractVerticle {

    private WebClient webClient;
    private Map<String, String> resultMap;

    @Override
    public void start() {
        webClient = WebClient.create(vertx);
        resultMap = vertx.sharedData().getLocalMap("resultMap");

        vertx.eventBus().<JsonObject>consumer(this.getClass().getName(), message -> {

            JsonObject replyJson = new JsonObject();

            String method = message.body().getString("method");

            if (method == null) method = "get";
            HttpMethod httpMethod = HttpMethod.GET;
            switch (method) {
                case "post":
                    httpMethod = HttpMethod.POST;
                    break;
                case "delete":
                    httpMethod = HttpMethod.DELETE;
                    break;
                case "put":
                    httpMethod = HttpMethod.PUT;
                    break;
                default:
                    break;
            }

            String abs = message.body().getString("abs");
            if (abs != null && !abs.startsWith("http")) {
                abs = "http://" + abs;
            }
            replyJson.put("abs", abs);
            JsonObject data = message.body().getJsonObject("data");

            String dataType = "send";
            ;
            if (message.body().getString("type") != null) {
                dataType = message.body().getString("type");
            }

            Future<HttpResponse<Buffer>> future = Future.future();
            try {
                HttpRequest<Buffer> httpRequest = webClient.requestAbs(httpMethod, abs);

                if (dataType.equals("submit") && data != null) {
                    MultiMap map = MultiMap.caseInsensitiveMultiMap();
                    for (String key : data.fieldNames()) {
                        if (data.getValue(key) != null)
                            map.add(key, data.getValue(key).toString());
                    }
                    if(map.isEmpty())
                        httpRequest.send(future);
                    else
                        httpRequest.sendForm(map, future);
                } else {
                    httpRequest.sendJsonObject(data, future);
                }

                future.setHandler(asyncResult -> {
                    if (asyncResult.succeeded()) {
                        HttpResponse response = asyncResult.result();
                        String contentType = response.getHeader("Content-Type");
                        String type = "xmlv";
                        String charset = null;
                        if (contentType != null) {
                            type = contentType.split(";")[0].trim();
                            charset = StringUtils.getCharsetFromContentType(contentType);
                        }
                        if (charset == null)
                            charset = "UTF-8";
                        String uuid = UUID.randomUUID().toString();
                        resultMap.put(uuid, response.bodyAsString(charset));
                        message.reply(replyJson.put("result", "succeed").put("type", type).put("charset", charset)
                                .put("content", uuid));
                    } else {
                        String uuid = UUID.randomUUID().toString();
                        resultMap.put(uuid, asyncResult.cause().toString());
                        message.reply(replyJson.put("result", "failed").put("content", uuid));
                    }
                });
            } catch (Throwable throwable) {
                String uuid = UUID.randomUUID().toString();
                resultMap.put(uuid, throwable.toString());
                message.reply(replyJson.put("result", "failed").put("content", uuid));
            }

        });
    }
}
