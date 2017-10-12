package com.whitewoodcity.core.node.conrol;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TextField extends Control{

    public TextField() {
        if (Platform.isFxApplicationThread()) body = new javafx.scene.control.TextField();
        else Platform.runLater(() -> body = new javafx.scene.control.TextField());
    }

    public TextField(String text) {
        if (Platform.isFxApplicationThread()) body = new javafx.scene.control.TextField(text);
        else Platform.runLater(() -> body = new javafx.scene.control.TextField(text));
    }

    public String getText() throws InterruptedException,ExecutionException {
        if (Platform.isFxApplicationThread()) {
            return ((javafx.scene.control.TextField)body).getText();
        }else{
            final FutureTask<String> task = new FutureTask<>(() -> {
                return ((javafx.scene.control.TextField)body).getText();
            });
            Platform.runLater(task);
            return task.get();
        }
    }

//    public String getText() {
//        return ((javafx.scene.control.TextField)body).getText();
//    }

    public StringProperty textProperty()  throws InterruptedException,ExecutionException {

        if (Platform.isFxApplicationThread()) {
            return ((javafx.scene.control.TextField)body).textProperty();
        }else{
            final FutureTask<StringProperty> task = new FutureTask<>(() -> {
                return ((javafx.scene.control.TextField)body).textProperty();
            });
            Platform.runLater(task);
            return task.get();
        }
    }

    public void setText(String text) {
        if (Platform.isFxApplicationThread()) ((javafx.scene.control.TextField)body).setText(text);
        else Platform.runLater(() -> ((javafx.scene.control.TextField)body).setText(text));
    }

    public StringProperty text_property() throws InterruptedException,ExecutionException  {
        return textProperty();
    }

    public StringProperty textproperty() throws InterruptedException,ExecutionException  {
        return textProperty();
    }

    public String getValue() throws InterruptedException,ExecutionException {
        return getText();
    }
}
