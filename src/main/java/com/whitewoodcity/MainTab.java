package com.whitewoodcity;

import io.vertx.core.Vertx;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTab extends Tab {
    TextField textField = new TextField();
    VBox vBox = new VBox();
    TextArea errorMessage = new TextArea();
    Parent content;
    WebClient client;

    public MainTab(TabPane tabPane, WebClient client) {
        this("New Tab", tabPane, client);
    }

    public MainTab(String title, TabPane tabPane, WebClient client) {
        this.client = client;
        this.setText(title);

        this.setContent(vBox);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(vBox.getSpacing()));
        vBox.getChildren().addAll(textField);

        vBox.prefHeightProperty()
                .bind(tabPane.heightProperty()
                        .subtract(textField.heightProperty())
                        .subtract(vBox.spacingProperty().multiply(3)));

        textField.setOnKeyPressed(value -> {
            if (value.getCode() == KeyCode.ENTER) {
                try {
                    client.getAbs(textField.getText())
                            .send(ar -> {
                                Platform.runLater(() -> {
                                    if (ar.succeeded()) {
                                        // Obtain response
                                        vBox.getChildren().removeAll(errorMessage, content);
                                        WebView webView = new WebView();
                                        webView.getEngine().load(textField.getText());
                                        textField.setText(webView.getEngine().getLocation());
                                        content = webView;
                                        vBox.getChildren().add(content);

                                        //get title from html string
                                        String body = ar.result().bodyAsString().replaceAll("\\s+", " ");
                                        Pattern p = Pattern.compile("<title>(.*?)</title>");
                                        Matcher m = p.matcher(body);
                                        if (m.find()) {
                                            this.setText(m.group(1));
                                        }
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
