package com.whitewoodcity.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.whitewoodcity.Main;
import com.whitewoodcity.core.api.JsApi;
import com.whitewoodcity.core.bean.CSS;
import com.whitewoodcity.core.bean.Script;
import com.whitewoodcity.core.bean.VXml;
import com.whitewoodcity.core.bean.XmlV;
import com.whitewoodcity.core.node.Button;
import com.whitewoodcity.core.parse.PageParser;
import com.whitewoodcity.util.Res;
import com.whitewoodcity.util.StringUtil;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TabContent implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    private HBox header;

    @FXML
    private TextField urlInput;

    @FXML
    private StackPane imgIcn;

    @FXML
    private StackPane container;
    private Rectangle containerClip = new Rectangle();

    private Tab tab;

    private WebClient client;

    private Node parent;
    private WebView webView;
    private PageParser pageParser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = WebClient.create(Main.vertx);
        header.setSpacing(0);
        header.setPadding(new Insets(10));
        urlInput.prefWidthProperty().bind(header.widthProperty().subtract(20).subtract(imgIcn.widthProperty()));
        pageParser=new PageParser();
        container.layoutYProperty().bind(header.heightProperty());

        containerClip.widthProperty().bind(container.widthProperty());
        containerClip.heightProperty().bind(container.heightProperty());
        container.setClip(containerClip);
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    @FXML
    public void loadEntry(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            loadUrl(keyEvent);
        }
    }

    @FXML
    private void loadUrl(Event event) {
        String url = urlInput.getText();
//        loadFxml(url);
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        loadWeb(url);
    }

    private void loadFxml(String url) {
        client.getAbs(url)
                .send(ar ->{
                    if(ar.succeeded()){
                        String content=ar.result().bodyAsString();
                        StringReader reader=new StringReader(content);
                        VXml vXml=pageParser.paresReader(reader, VXml.class);
                        BufferedWriter fos = null;
                        InputStream is=null;
                        try {
                            //第一步 载入fxml
                            is=new ByteArrayInputStream(vXml.getfXml().getFxml().getBytes());
                            FXMLLoader loader=new FXMLLoader();
                            Parent parent=loader.load(is);
                            //第二步 载入脚本绑定
                            ScriptEngineManager manager=new ScriptEngineManager();
                            ScriptEngine js=manager.getEngineByName("JavaScript");
                            js.put("fx",new JsApi(parent));
                            js.eval("function $(selector){return fx.findView(selector)}");
                            List<Script> scripts=vXml.getScripts();
                            for (Script script:scripts){
                                String type=script.type;
                                if(!StringUtil.isEmpty(type)
                                        &&!StringUtil.isEmpty(script.script)
                                        &&type.equalsIgnoreCase("javascript")){
                                    js.eval(script.script);
                                }
                            }
                            //第三步 应用css
                            List<CSS> csses=vXml.getCsses();
                            if(csses.size()>0){
                                File cssFile=Res.getTempFile("css");
                                fos=new BufferedWriter(new FileWriter(cssFile));
                                for (CSS css:csses){
                                    String cssStr=css.getCss();
                                    if(StringUtil.isEmpty(cssStr)){
                                        continue;
                                    }
                                    fos.write(cssStr);
                                    fos.newLine();
                                }
                                fos.flush();
                                fos.close();
                                parent.getStylesheets().add(cssFile.toURI().toURL().toExternalForm());
                            }

                            Platform.runLater(()->{
                                removeParent();
                                addNode(parent);
                            });
                        } catch (IOException | ScriptException e) {
//                            e.printStackTrace();
                            Platform.runLater(() -> {
                                handleExceptionMessage(ar.cause());
                            });
                        } finally {
                            try {
                                if(fos!=null){
                                    fos.close();
                                }
                                if (is != null) {
                                    is.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }else{
                        Platform.runLater(() -> {
                            handleExceptionMessage(ar.cause());
                        });
                    }
                });
    }

    private void loadWeb(final String url) {
        try {
            client.getAbs(url).send(ar -> {
                if (ar.succeeded()) {
                    ParentType type;
                    if (url.endsWith("xmlv") ||
                            (ar.result().getHeader("Content-Type")!=null&&
                                    ar.result().getHeader("Content-Type").endsWith("xmlv"))) {
                        type = ParentType.GROUP;
                    } else {
                        type = ParentType.WEB_VIEW;
                    }
                    String result = ar.result().bodyAsString();
                    Platform.runLater(() -> processParent(type, result, url));
                } else {
                    Throwable throwable = ar.cause();
                    Platform.runLater(() -> handleExceptionMessage(throwable));
                }
            });
        } catch (Exception e) {
            handleExceptionMessage(e);
        }
    }

    private void handleExceptionMessage(Throwable e, String message) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        processParent(ParentType.ERROR_MESSAGE, sw.toString(), message);
    }

    private void handleExceptionMessage(Throwable e) {
        handleExceptionMessage(e, e.getMessage());
    }

    XmlMapper xmlMapper = new XmlMapper();

    private void processParent(ParentType type, String result, String urlOrMsg){
        removeParent();
//        System.out.println(type);
        switch (type) {
            case GROUP:
                container.setPadding(new Insets(0));
                com.whitewoodcity.core.node.Pane pane = new com.whitewoodcity.core.node.Pane();
                try {
                    XmlV xmlV = xmlMapper.readValue(result, XmlV.class);

                    ScriptEngineManager manager = new ScriptEngineManager();
                    ScriptEngine engine = manager.getEngineByName("JavaScript");

                    parent = xmlV.getJson().generateNode(engine);
                    System.out.println(xmlV.getJson());
                    engine.eval("button001.action = function (value){print(button001.id);button001.x = button001.x+10;}");

                } catch (Exception e) {
                    handleExceptionMessage(e, result);
                    return;
                }
                break;
            case ERROR_MESSAGE:
                TextArea errorMsg = new TextArea();
                errorMsg.setPrefHeight(container.getHeight() - 20);
                errorMsg.setText(urlOrMsg+result);
                container.setPadding(new Insets(10));
                parent = errorMsg;
                tab.textProperty().unbind();
                tab.setText(urlOrMsg);
                break;
            default:
                container.setPadding(new Insets(0));
                if(webView==null){
                    webView = new WebView();
                }
                webView.getEngine().loadContent(result);
                tab.textProperty().unbind();
                tab.textProperty().bind(webView.getEngine().titleProperty());
                webView.getEngine().load(urlOrMsg);

                parent = webView;
                break;
        }

        container.getChildren().add(0,parent);
    }

    public void removeParent() {
        container.getChildren().clear();
    }

    public HBox getHeader() {
        return header;
    }

    public StackPane getContainer() {
        return container;
    }

    public void addNode(Parent node){
        parent=node;
        node.setLayoutY(header.getLayoutY()+header.getHeight());
        pane.getChildren().add(node);
    }
}
