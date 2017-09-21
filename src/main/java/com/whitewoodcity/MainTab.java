package com.whitewoodcity;

import io.vertx.ext.web.client.WebClient;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class MainTab extends Tab {
    TextField textField = new TextField("http://");
    VBox vBox = new VBox();
    TextArea errorMessage = new TextArea();
    Parent content;
    WebClient client;

    public MainTab(TabPane tabPane, WebClient client) {
        this("New Tab", tabPane, client);
    }

    public MainTab(String defaultTitle, TabPane tabPane, WebClient client) {
        this.client = client;
        this.setText(defaultTitle);

        this.setContent(vBox);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(vBox.getSpacing()));
        vBox.getChildren().addAll(textField);

        vBox.prefHeightProperty()
                .bind(tabPane.heightProperty()
                        .subtract(textField.heightProperty())
                        .subtract(vBox.spacingProperty().multiply(3)));

        textField.setOnKeyPressed(value -> {
            String url = textField.getText();
            if (!url.startsWith("http"))
                url = "http://" + url;
            if (value.getCode() == KeyCode.ENTER) {
                try {
                    final String immutableUrl = url;
                    client.getAbs(immutableUrl)
                            .send(ar -> {
                                Platform.runLater(() -> {
                                    if (ar.succeeded()) {
                                        // Obtain response
                                        WebView webView = new WebView();
                                        webView.getEngine().loadContent(ar.result().bodyAsString());
                                        vBox.getChildren().removeAll(errorMessage, content);
                                        content = webView;
                                        vBox.getChildren().add(content);

                                        //get title from html string
                                        textProperty().unbind();
                                        textProperty().bind(webView.getEngine().titleProperty());

                                        webView.getEngine().load(immutableUrl);
                                    } else {
                                        handleErrorMessage(ar.cause().getMessage());
                                    }
                                });
                            });
                } catch (Exception e) {
                    handleErrorMessage(e.getMessage());
                }
            }
        });
    }

    private void handleErrorMessage(String message) {
        errorMessage.clear();
        errorMessage.appendText(message);
        vBox.getChildren().removeAll(errorMessage, content);
        vBox.getChildren().addAll(errorMessage);
    }
}
