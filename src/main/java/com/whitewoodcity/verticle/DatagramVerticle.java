package com.whitewoodcity.verticle;

import com.whitewoodcity.Main;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.json.JsonObject;
import javafx.application.Platform;

import java.util.HashMap;
import java.util.Map;

public class DatagramVerticle extends AbstractVerticle {

    DatagramSocket datagramSocket;

    private Map<String, DatagramSocket> socketMap = new HashMap<>();

    @Override
    public void start() throws Exception {

        vertx.eventBus().<JsonObject>consumer(this.getClass().getName(), message -> {

            String id;

            switch (message.body().getString("method")) {
                case "stop":
                case "close":
                    id = message.body().getString("id");
                    if (socketMap.get(id) != null) {
                        socketMap.remove(id).close();
                    }
                    Main.bufferMap.remove(id);
                    break;
                case "send":
                    id = message.body().getString("id");
                    if (socketMap.get(id) != null) {
                        datagramSocket = socketMap.get(id);
                        Object value = message.body().getValue("value");
                        int port = message.body().getInteger("port");
                        String address = message.body().getString("address");
                        datagramSocket.send(value.toString(), port, address, ar -> {
                        });
                    }
                    break;
                case "listen":
                    id = message.body().getString("id");
                    if (socketMap.get(id) != null) {
                        socketMap.get(id).close();
                    }
                    datagramSocket = vertx.createDatagramSocket();
                    socketMap.put(id, datagramSocket);

                    datagramSocket.listen(message.body().getInteger("port"), "0.0.0.0", ar -> {
                        if (ar.succeeded()) {

                            if (Main.bufferMap.get(id) == null) {
                                Main.bufferMap.put(id, Buffer.buffer());
                            }
                            datagramSocket.handler(packet -> {
                                Platform.runLater(() -> {
                                    int maxLen = 0;
                                    if (message.body().getValue("length") != null) {
                                        maxLen = message.body().getInteger("length") * 4;
                                    }
                                    if (maxLen <= 0) {
                                        Main.bufferMap.put(id, packet.data());
                                    } else {
                                        Buffer buffer = Main.bufferMap.get(id);
                                        buffer.appendBuffer(packet.data());
                                        if (buffer.length() > maxLen * 4)
                                            Main.bufferMap.put(id, buffer.getBuffer(buffer.length() - maxLen * 4, buffer.length()));
                                    }
                                });
                            });
                            message.reply(new JsonObject().put("result", "OK"));
                        } else {
                            message.reply(new JsonObject().put("result", ar.cause().getMessage()));
                        }
                    });

                    break;
                default:
                    break;
            }
        });
    }
}
