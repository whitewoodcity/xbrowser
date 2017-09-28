package com.whitewoodcity.core.node.conrol;

import com.whitewoodcity.controller.TabContent;
import javafx.beans.property.StringProperty;

public class Hyperlink extends ButtonBase {

    TabContent app;
    private String value;

    public Hyperlink() {
        body = new javafx.scene.control.Hyperlink();
        setAction(event -> {
            if (value == null) app.loadWeb(((javafx.scene.control.Hyperlink) body).getText());
            else app.loadWeb(value);
        });
    }

    public Hyperlink(String text) {
        body = new javafx.scene.control.Hyperlink(text);
        setAction(event -> {
            if (value == null) app.loadWeb(((javafx.scene.control.Hyperlink) body).getText());
            else app.loadWeb(value);
        });
    }

    public Hyperlink(String text, javafx.scene.Node graphic) {
        body = new javafx.scene.control.Hyperlink(text, graphic);
        setAction(event -> {
            if (value == null) app.loadWeb(((javafx.scene.control.Hyperlink) body).getText());
            else app.loadWeb(value);
        });
    }

    public void setApp(TabContent app) {
        this.app = app;
    }

    @Override
    public String getValue() {
        if (value == null) return ((javafx.scene.control.Hyperlink) body).getText();
        else return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return ((javafx.scene.control.Hyperlink)body).getText();
    }

    public StringProperty textProperty() {
        return ((javafx.scene.control.Hyperlink)body).textProperty();
    }

    public void setText(String text) {
        ((javafx.scene.control.Hyperlink)body).setText(text);
    }

    public StringProperty text_property() {
        return textProperty();
    }

    public StringProperty textproperty() {
        return textProperty();
    }

}
