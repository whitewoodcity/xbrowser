package com.whitewoodcity.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.whitewoodcity.Main;
import com.whitewoodcity.core.bean.XmlV;
import io.vertx.ext.web.client.WebClient;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class TabContent implements Initializable {

    @FXML
    private VBox vBox;

    @FXML
    private HBox header;

    @FXML
    private TextField urlInput;

    @FXML
    private StackPane container;

    private Tab tab;

    private WebClient client;

    private WebView webView;

    private ParentType lastParent=ParentType.NONE;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = WebClient.create(Main.vertx);
        container.prefHeightProperty().bind(vBox.heightProperty().subtract(header.getHeight()));

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
        if (url.startsWith("http://") || url.startsWith("https://")) {
            loadWeb(url);
        }
    }

    private void loadWeb(String url) {
        try {
            client.getAbs(url).send(ar -> {
                if (ar.succeeded()) {
                    Platform.runLater(() -> {
                        buildParent(ParentType.WEB_VIEW,ar.result().bodyAsString(),url);

                    });
                } else {
                    Platform.runLater(() -> {
                        handleExceptionMessage(ar.cause());
                    });
                }


            });
        } catch (Exception e) {
            handleExceptionMessage(e.getCause());
        }
    }

    private void handleExceptionMessage(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        buildParent(ParentType.ERROR_MESSAGE,sw.toString(),null);

    }


    XmlMapper xmlMapper = new XmlMapper();

    private void buildParent(ParentType type, String result, String immutableUrl){
        removeNode(type);
        switch (type) {
            case GROUP:
                Group group = new Group();
                try {
                    XmlV xmlV = xmlMapper.readValue(result, XmlV.class);
                    Rectangle rectangle = new Rectangle();
                    rectangle.setFill(Color.RED);
                    rectangle.setWidth(100);
                    rectangle.setHeight(100);
                    group.getChildren().add(rectangle);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case ERROR_MESSAGE:
                TextArea errorMsg=new TextArea();
                errorMsg.setText(result);
                addNode(errorMsg);
                break;
                default:
                    if(webView==null){
                        webView=new WebView();
                        addNode(webView);
                    }
                    webView.getEngine().loadContent(result);
                    tab.textProperty().unbind();
                    tab.textProperty().bind(webView.getEngine().titleProperty());
                    webView.getEngine().load(immutableUrl);
                    return ;
        }
    }


    public void addNode(Node node){
        container.getChildren().add(node);
    }

    public void removeNode(ParentType current){
        switch (lastParent){
            case WEB_VIEW:
                if(current!=ParentType.WEB_VIEW){
                    container.getChildren().removeAll(webView);
                    webView=null;
                }
                break;
                default:
                    clearAll();
//            case REGION:
//            case GROUP:
//            case ERROR_MESSAGE:
//                break;
        }
        lastParent=current;
    }

    private void clearAll(){
        ObservableList<Node> nodes=container.getChildren();
        if(nodes.size()>0){
            nodes.remove(0,nodes.size());

        }
    }

}
