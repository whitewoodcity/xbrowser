package com.whitewoodcity.controller;

import com.whitewoodcity.Main;
import io.vertx.ext.web.client.WebClient;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

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
                String result;
                if(ar.succeeded()) result = ar.result().bodyAsString();
                else result = ar.cause().getMessage();

                Platform.runLater(() -> {
                    if (ar.succeeded()) {
                        // Obtain response
                        WebView webView = new WebView();
                        webView.prefHeightProperty().bind(vBox.heightProperty().subtract(urlInput.heightProperty()));
                        webView.getEngine().loadContent(result);
                        webView.getEngine().loadContent(result);
                        container.getChildren().removeAll(errorMessage);
                        vBox.getChildren().add(webView);

                        //get title from html string
                        tab.textProperty().unbind();
                        tab.textProperty().bind(webView.getEngine().titleProperty());

                        webView.getEngine().load(url);
                    } else {
                        handleErrorMessage(result);
                    }
                });
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleErrorMessage(String message) {
        errorMessage.clear();
        errorMessage.appendText(message);
        vBox.getChildren().removeAll(errorMessage);
        vBox.getChildren().addAll(errorMessage);
    }
}
