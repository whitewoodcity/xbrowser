package com.whitewoodcity.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.whitewoodcity.Main;
import com.whitewoodcity.core.bean.XmlV;
import io.vertx.ext.web.client.WebClient;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class TabContent implements Initializable{

    @FXML
    private VBox vBox;

    @FXML
    private TextField urlInput;

    @FXML
    private StackPane container;

    private Tab tab;

    private TextArea errorMessage = new TextArea();

    private WebClient client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client=WebClient.create(Main.vertx) ;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    @FXML
    private void loadUrl(Event event){
        String url = urlInput.getText();
        if(url.startsWith("http://")||url.startsWith("https://")){
            loadWeb(url);
        }
    }

    private void loadWeb(String url){
        try {
            client.getAbs(url).send(ar -> {
                if(ar.succeeded()){
                    Platform.runLater(() -> {

                            // Obtain response
                            WebView webView = new WebView();
                            webView.prefHeightProperty().bind(vBox.heightProperty().subtract(urlInput.heightProperty()));
                            webView.getEngine().loadContent(ar.result().bodyAsString());
                            container.getChildren().removeAll(errorMessage);
                            vBox.getChildren().add(webView);

                            //get title from html string
                            tab.textProperty().unbind();
                            tab.textProperty().bind(webView.getEngine().titleProperty());

                            webView.getEngine().load(url);

                    });
                } else {
                    Platform.runLater(()->{
                        handleExceptionMessage(ar.cause());
                    });
                }


            });
        }catch (Exception e){
            handleExceptionMessage(e);
            e.printStackTrace();
        }
    }

    private void handleExceptionMessage(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        handleErrorMessage(sw.toString());
    }

    private void handleErrorMessage(String message) {
        errorMessage.clear();
        errorMessage.appendText(message);
        vBox.getChildren().removeAll(errorMessage);
        vBox.getChildren().addAll(errorMessage);
        errorMessage.prefHeightProperty().bind(vBox.heightProperty().subtract(urlInput.heightProperty()));
    }

    XmlMapper xmlMapper = new XmlMapper();

    private Parent build(ParentType type, String result, DoubleBinding height, String immutableUrl) throws Exception {
        switch (type) {
            case GROUP:
                Group group = new Group();
                XmlV xmlV = xmlMapper.readValue(result, XmlV.class);
                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.RED);
                rectangle.setWidth(100);
                rectangle.setHeight(100);
                group.getChildren().add(rectangle);
                return group;
            default:
                WebView webView = new WebView();
                webView.getEngine().loadContent(result);
                webView.prefHeightProperty().bind(height);

                //get title from html string
                tab.textProperty().unbind();
                tab.textProperty().bind(webView.getEngine().titleProperty());

                webView.getEngine().load(immutableUrl);
                return webView;
        }
    }

}
