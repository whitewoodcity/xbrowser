package com.whitewoodcity.core.node.conrol;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Button extends ButtonBase {

    public Button() {
        if (Platform.isFxApplicationThread()) body = new javafx.scene.control.Button();
        else Platform.runLater(() -> body = new javafx.scene.control.Button());
    }

    public Button(String text) {
        if (Platform.isFxApplicationThread()) body = new javafx.scene.control.Button(text);
        else Platform.runLater(() -> body = new javafx.scene.control.Button(text));
    }

    public Button(String text, javafx.scene.Node graphic) {
        if (Platform.isFxApplicationThread()) body = new javafx.scene.control.Button(text, graphic);
        else Platform.runLater(() -> body = new javafx.scene.control.Button(text, graphic));
    }

    public String getText() throws InterruptedException, ExecutionException {
        if (Platform.isFxApplicationThread()) {
            return ((javafx.scene.control.Button) body).getText();
        }
        final FutureTask<String> task = new FutureTask<>(() -> ((javafx.scene.control.Button) body).getText());
        Platform.runLater(task);
        return task.get();
    }

    public StringProperty textProperty() throws InterruptedException, ExecutionException {
        if (Platform.isFxApplicationThread()) {
            return ((javafx.scene.control.Button) body).textProperty();
        }
        final FutureTask<StringProperty> task = new FutureTask<>(() -> ((javafx.scene.control.Button) body).textProperty());
        Platform.runLater(task);
        return task.get();
    }

    public void setText(String text) {
        if (Platform.isFxApplicationThread()) {
            ((javafx.scene.control.Button) body).setText(text);
        } else Platform.runLater(() -> ((javafx.scene.control.Button) body).setText(text));
    }

    public StringProperty text_property() throws InterruptedException, ExecutionException {
        return textProperty();
    }

    public StringProperty textproperty() throws InterruptedException, ExecutionException {
        return textProperty();
    }

    public String getValue() throws InterruptedException, ExecutionException {
        return getText();
    }
}
