package com.whitewoodcity.core.node.conrol;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Label extends Control{

    public Label() {

        if (Platform.isFxApplicationThread()) body = new javafx.scene.control.Label();
        else Platform.runLater(() -> body = new javafx.scene.control.Label());

    }

    public Label(String text) {
        if (Platform.isFxApplicationThread()) body = new javafx.scene.control.Label(text);
        else Platform.runLater(() -> body = new javafx.scene.control.Label(text));
    }

    public Label(String text, javafx.scene.Node graphic) {
        if (Platform.isFxApplicationThread()) body = new javafx.scene.control.Label(text,graphic);
        else Platform.runLater(() -> body = new javafx.scene.control.Label(text,graphic));

    }

    public String getText() throws InterruptedException,ExecutionException {
        if (Platform.isFxApplicationThread()) {
            return ((javafx.scene.control.Label)body).getText();
        }else{
            final FutureTask<String> task = new FutureTask<>(() -> {
                return ((javafx.scene.control.Label)body).getText();
            });
            Platform.runLater(task);
            return task.get();
        }
    }

    public StringProperty textProperty()throws InterruptedException,ExecutionException {

        if (Platform.isFxApplicationThread()) {
            return ((javafx.scene.control.Label)body).textProperty();
        }else{
            final FutureTask<StringProperty> task = new FutureTask<>(() -> {
                return ((javafx.scene.control.Label)body).textProperty();
            });
            Platform.runLater(task);
            return task.get();
        }


    }

    public void setText(String text) {
        if (Platform.isFxApplicationThread()) ((javafx.scene.control.Label)body).setText(text);
        else Platform.runLater(() -> ((javafx.scene.control.Label)body).setText(text));
    }

    public StringProperty text_property()throws InterruptedException,ExecutionException {
        return textProperty();
    }

    public StringProperty textproperty()throws InterruptedException,ExecutionException {
        return textProperty();
    }

    public String getValue()throws InterruptedException,ExecutionException{
        return getText();
    }
}
