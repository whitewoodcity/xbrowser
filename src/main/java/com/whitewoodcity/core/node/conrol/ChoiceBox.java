package com.whitewoodcity.core.node.conrol;

import io.vertx.core.json.JsonArray;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ChoiceBox<T> extends Control{

    public ChoiceBox() {
        if (Platform.isFxApplicationThread()) body = new javafx.scene.control.ChoiceBox<T>();
        else Platform.runLater(() -> body = new javafx.scene.control.ChoiceBox<T>());
    }

    public ChoiceBox(ObservableList<T> items) {
        if (Platform.isFxApplicationThread()) body = new javafx.scene.control.ChoiceBox<>(items);
        else Platform.runLater(() -> body = new javafx.scene.control.ChoiceBox<>(items));
    }

    public String getValue() throws InterruptedException,ExecutionException{

        if (Platform.isFxApplicationThread()) {
            if(((javafx.scene.control.ChoiceBox)body).getValue() == null) return "";
            else return ((javafx.scene.control.ChoiceBox)body).getValue().toString();
        }
        final FutureTask<String> task = new FutureTask<>(() -> {
            if(((javafx.scene.control.ChoiceBox)body).getValue() == null) return "";
            else return ((javafx.scene.control.ChoiceBox)body).getValue().toString();
        });
        Platform.runLater(task);
        return task.get();

    }

    public void setItems(JsonArray items){

        if (Platform.isFxApplicationThread()){
            ObservableList<String> list = FXCollections.observableArrayList(items.getList());
            ((javafx.scene.control.ChoiceBox)body).setItems(list);
        }
        else Platform.runLater(() -> {
            ObservableList<String> list = FXCollections.observableArrayList(items.getList());
            ((javafx.scene.control.ChoiceBox)body).setItems(list);
        });
    }

    public void setValue(String selected){
        if (Platform.isFxApplicationThread()){
            ((javafx.scene.control.ChoiceBox)body).setValue(selected);
        }
        else Platform.runLater(() -> {
            ((javafx.scene.control.ChoiceBox)body).setValue(selected);
        });
    }
}
