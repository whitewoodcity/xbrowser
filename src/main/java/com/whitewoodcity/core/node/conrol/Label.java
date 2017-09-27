package com.whitewoodcity.core.node.conrol;

import javafx.beans.property.StringProperty;

public class Label extends Control{

    public Label() {
        body = new javafx.scene.control.Label();
    }

    public Label(String text) {
        body = new javafx.scene.control.Label(text);
    }

    public Label(String text, javafx.scene.Node graphic) {
        body = new javafx.scene.control.Label(text, graphic);
    }

    public String getText() {
        return ((javafx.scene.control.Label)body).getText();
    }

    public StringProperty textProperty() {
        return ((javafx.scene.control.Label)body).textProperty();
    }

    public void setText(String text) {
        ((javafx.scene.control.Label)body).setText(text);
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
