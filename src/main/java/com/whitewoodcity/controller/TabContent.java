package com.whitewoodcity.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.whitewoodcity.Main;
import com.whitewoodcity.core.bean.XmlV;
import io.vertx.ext.web.client.WebClient;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
    private Group group;

    @FXML
    private HBox header;

    @FXML
    private TextField urlInput;

    @FXML
    private StackPane imgIcn;

    private Tab tab;

    private WebClient client;

    private Parent parent;
    private WebView webView;

    private ParentType lastParent = ParentType.NONE;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = WebClient.create(Main.vertx);
        header.setSpacing(0);
        header.setPadding(new Insets(10));
        urlInput.prefWidthProperty().bind(header.widthProperty().subtract(20).subtract(imgIcn.widthProperty()));
//        container.prefHeightProperty().bind(vBox.heightProperty().subtract(header.getHeight()));
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
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        loadWeb(url);
    }

    private void loadWeb(String url) {
        try {
            client.getAbs(url).send(ar -> {
                if (ar.succeeded()) {
                    ParentType type;
                    if (url.endsWith("xmlv") || ar.result().getHeader("Content-Type").endsWith("xmlv")) {
                        type = ParentType.GROUP;
                    } else {
                        type = ParentType.WEB_VIEW;
                    }
                    String result = ar.result().bodyAsString();
                    Platform.runLater(() -> {
                        processParent(type, result, url);
                    });
                } else {
                    Throwable throwable = ar.cause();
                    Platform.runLater(() -> {
                        handleExceptionMessage(throwable);
                    });
                }
            });
        } catch (Exception e) {
            handleExceptionMessage(e);
        }
    }

    private void handleExceptionMessage(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        processParent(ParentType.ERROR_MESSAGE, sw.toString(), null);
    }

    XmlMapper xmlMapper = new XmlMapper();

    private void processParent(ParentType type, String result, String url) {
        removeParent();
        switch (type) {
            case GROUP:
                Group group = new Group();
                try {
                    XmlV xmlV = xmlMapper.readValue(result, XmlV.class);
                    Rectangle rectangle = new Rectangle();
                    rectangle.setFill(Color.GREEN);
                    rectangle.setLayoutX(2000);
                    rectangle.setWidth(3000);
                    rectangle.setHeight(100);
                    group.getChildren().add(rectangle);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case ERROR_MESSAGE:
                TextArea errorMsg = new TextArea();
                errorMsg.setText(result);
                errorMsg.setLayoutY(header.getLayoutY()+header.getHeight());
                errorMsg.prefWidthProperty().bind(tab.getTabPane().widthProperty());
                this.group.getChildren().add(errorMsg);
                parent = errorMsg;
                break;
            default:
                if(webView==null){
                    webView = new WebView();
                    webView.setLayoutY(header.getLayoutY()+header.getHeight());
                    webView.prefWidthProperty().bind(tab.getTabPane().widthProperty());
                }
                webView.getEngine().loadContent(result);
                tab.textProperty().unbind();
                tab.textProperty().bind(webView.getEngine().titleProperty());
                webView.getEngine().load(url);

                this.group.getChildren().add(webView);
                parent = webView;
                break;
        }
    }

    public void removeParent() {
        group.getChildren().remove(parent);
    }

    public HBox getHeader() {
        return header;
    }
}
