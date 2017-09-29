package com.whitewoodcity.core.node.conrol;

import io.vertx.core.json.JsonArray;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ChoiceBox<T> extends Control{

    public ChoiceBox() {
        body = new javafx.scene.control.ChoiceBox<T>();
    }

    public ChoiceBox(ObservableList<T> items) {
        body = new javafx.scene.control.ChoiceBox<>(items);
    }

    public String getValue(){
        if(((javafx.scene.control.ChoiceBox)body).getValue() == null) return "";
        else return ((javafx.scene.control.ChoiceBox)body).getValue().toString();
    }

    public void setItems(JsonArray items){
        ObservableList<String> list = FXCollections.observableArrayList(items.getList());
        ((javafx.scene.control.ChoiceBox)body).setItems(list);
    }

    public void setValue(String selected){
        ((javafx.scene.control.ChoiceBox)body).setValue(selected);
    }
}
