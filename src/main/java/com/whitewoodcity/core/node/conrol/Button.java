package com.whitewoodcity.core.node.conrol;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Button extends Control{

    public Button() {
        body = new javafx.scene.control.Button();
    }

    public Button(String text) {
        body = new javafx.scene.control.Button(text);
    }

    public Button(String text, javafx.scene.Node graphic) {
        body = new javafx.scene.control.Button(text, graphic);
    }

    public void setAction(EventHandler<ActionEvent> value){
        ((javafx.scene.control.Button)body).setOnAction(value);
    }

    public void setaction(EventHandler<ActionEvent> value){
        setAction(value);
    }

    public void set_action(EventHandler<ActionEvent> value){
        setAction(value);
    }

    public String getText() {
        return ((javafx.scene.control.Button)body).getText();
    }

    public StringProperty textProperty() {
        return ((javafx.scene.control.Button)body).textProperty();
    }

    public void setText(String text) {
        ((javafx.scene.control.Button)body).setText(text);
    }

    public StringProperty text_property() {
        return textProperty();
    }

    public StringProperty textproperty() {
        return textProperty();
    }

    public String getValue(){
        return getText();
    }
}
