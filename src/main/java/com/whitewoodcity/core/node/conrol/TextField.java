package com.whitewoodcity.core.node.conrol;

import javafx.beans.property.StringProperty;

public class TextField extends Control{

    public TextField() {
        body = new javafx.scene.control.TextField();
    }

    public TextField(String text) {
        body = new javafx.scene.control.TextField(text);
    }

    public String getText() {
        return ((javafx.scene.control.TextField)body).getText();
    }

    public StringProperty textProperty() {
        return ((javafx.scene.control.TextField)body).textProperty();
    }

    public void setText(String text) {
        ((javafx.scene.control.TextField)body).setText(text);
    }

    public StringProperty text_property() {
        return textProperty();
    }

    public StringProperty textproperty() {
        return textProperty();
    }
}
