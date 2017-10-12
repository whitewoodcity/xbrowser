package com.whitewoodcity.core.node.conrol;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public abstract class ButtonBase extends Control{

    public void setAction(EventHandler<ActionEvent> value){
        if(Platform.isFxApplicationThread()) ((javafx.scene.control.ButtonBase)body).setOnAction(value);
        else Platform.runLater(()->((javafx.scene.control.ButtonBase)body).setOnAction(value));
    }

    public void setaction(EventHandler<ActionEvent> value){
        setAction(value);
    }

    public void set_action(EventHandler<ActionEvent> value){
        setAction(value);
    }

}
