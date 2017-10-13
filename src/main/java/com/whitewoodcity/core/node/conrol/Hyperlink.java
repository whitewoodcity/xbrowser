package com.whitewoodcity.core.node.conrol;

import com.whitewoodcity.controller.TabContent;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Hyperlink extends ButtonBase {

    TabContent app;
    private String value;

    public Hyperlink() {
        if(Platform.isFxApplicationThread()){
            body = new javafx.scene.control.Hyperlink();
            setAction(event -> {
                if (value == null) app.load(((javafx.scene.control.Hyperlink) body).getText());
                else app.load(value);
            });
        }
        else Platform.runLater(()->{
            body = new javafx.scene.control.Hyperlink();
            setAction(event -> {
                if (value == null) app.load(((javafx.scene.control.Hyperlink) body).getText());
                else app.load(value);
            });
        });
    }

    public Hyperlink(String text) {
        if(Platform.isFxApplicationThread()){
            body = new javafx.scene.control.Hyperlink(text);
            setAction(event -> {
                if (value == null) app.load(((javafx.scene.control.Hyperlink) body).getText());
                else app.load(value);
            });
        }
        else Platform.runLater(()->{
            body = new javafx.scene.control.Hyperlink(text);
            setAction(event -> {
                if (value == null) app.load(((javafx.scene.control.Hyperlink) body).getText());
                else app.load(value);
            });
        });
    }

    public Hyperlink(String text, javafx.scene.Node graphic) {
        if(Platform.isFxApplicationThread()){
            body = new javafx.scene.control.Hyperlink(text,graphic);
            setAction(event -> {
                if (value == null) app.load(((javafx.scene.control.Hyperlink) body).getText());
                else app.load(value);
            });
        }
        else Platform.runLater(()->{
            body = new javafx.scene.control.Hyperlink(text,graphic);
            setAction(event -> {
                if (value == null) app.load(((javafx.scene.control.Hyperlink) body).getText());
                else app.load(value);
            });
        });
    }

    public void setApp(TabContent app) {
        this.app = app;
    }

    @Override
    public String getValue() throws InterruptedException,ExecutionException{
        if (value == null){
            if (Platform.isFxApplicationThread()) {
                return ((javafx.scene.control.Hyperlink) body).getText();
            }else{
                final FutureTask<String> task = new FutureTask<>(() -> {
                    return ((javafx.scene.control.Hyperlink) body).getText();
                });
                Platform.runLater(task);
                return task.get();
            }
        } else return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() throws InterruptedException,ExecutionException{
        if (Platform.isFxApplicationThread()) {
            return ((javafx.scene.control.Hyperlink)body).getText();
        }else{
            final FutureTask<String> task = new FutureTask<>(() -> {
                return ((javafx.scene.control.Hyperlink)body).getText();
            });
            Platform.runLater(task);
            return task.get();
        }
    }

    public StringProperty textProperty() throws InterruptedException,ExecutionException {
        if (Platform.isFxApplicationThread()) {
            return ((javafx.scene.control.Hyperlink)body).textProperty();
        }
        final FutureTask<StringProperty> task = new FutureTask<>(() -> ((javafx.scene.control.Hyperlink)body).textProperty());
        Platform.runLater(task);
        return task.get();
    }

    public void setText(String text) {
        if(Platform.isFxApplicationThread()) ((javafx.scene.control.Hyperlink)body).setText(text);
        else Platform.runLater(()->((javafx.scene.control.Hyperlink)body).setText(text));
    }

    public StringProperty text_property()throws InterruptedException,ExecutionException {
        return textProperty();
    }

    public StringProperty textproperty()throws InterruptedException,ExecutionException {
        return textProperty();
    }

}
