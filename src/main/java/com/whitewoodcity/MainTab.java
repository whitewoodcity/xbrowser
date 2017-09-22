package com.whitewoodcity;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.whitewoodcity.core.bean.XmlV;
import io.vertx.ext.web.client.WebClient;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MainTab extends Tab {
    TextField addressBar = new TextField("http://");
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
        vBox.getChildren().addAll(addressBar);

        vBox.prefHeightProperty()
                .bind(tabPane.heightProperty());

        addressBar.setOnKeyPressed(value -> {
            String url = addressBar.getText();
            if (!url.startsWith("http")) url = "http://" + url;
            if (value.getCode() == KeyCode.ENTER) {
                try {
                    final String immutableUrl = url;
                    client.getAbs(immutableUrl).send(ar -> {

                        Platform.runLater(() -> {
                            if (ar.succeeded()) {
                                // Obtain response
                                String result = ar.result().bodyAsString();
                                vBox.getChildren().removeAll(errorMessage, content);
                                try {
                                    ParentType type = ParentType.WEB_VIEW;
                                    if (immutableUrl.endsWith("xmlv") || immutableUrl.endsWith("jsonv")) {
                                        type = ParentType.GROUP;
                                    }
                                    content = build(type, result,
                                            vBox.heightProperty().subtract(addressBar.heightProperty()), immutableUrl);
                                    vBox.getChildren().add(content);
                                } catch (Exception e) {
                                    handleResultAndException(result, e);
                                }
                            } else {
                                handleExceptionMessage(ar.cause());
                            }
                        });
                    });
                } catch (Exception e) {
                    handleExceptionMessage(e);
                }
            }
        });
    }

    private void handleResultAndException(String result, Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        handleErrorMessage(result + "\n" + sw.toString());
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
        vBox.getChildren().removeAll(errorMessage, content);
        vBox.getChildren().addAll(errorMessage);
        errorMessage.prefHeightProperty().bind(vBox.heightProperty().subtract(addressBar.heightProperty()));
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
                textProperty().unbind();
                textProperty().bind(webView.getEngine().titleProperty());

                webView.getEngine().load(immutableUrl);
                return webView;
        }
    }

}

enum ParentType {
    GROUP, REGION, WEB_VIEW
}