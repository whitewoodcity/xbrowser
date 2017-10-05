package com.whitewoodcity.core.node.conrol;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import java.util.*;

public class TableView extends Control{

    private Map<String, TableColumn> columnMap = new HashMap<>();
    ObservableList<Item> items = FXCollections.observableArrayList();

    public TableView(){
        body = new javafx.scene.control.TableView<Item>();
        ((javafx.scene.control.TableView)body).setItems(items);
        ((javafx.scene.control.TableView)body).setEditable(true);
        ((javafx.scene.control.TableView)body).getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void setHeader(JsonArray header){
        if(header==null) return;
        for(Object object:header){
            if(object instanceof String) {
                TableColumn column = processStringColumnName((String) object);
                ((javafx.scene.control.TableView)body).getColumns().add(column);
            }
            else if(object instanceof JsonObject) {
                TableColumn column = processJsonObjectColumnName((JsonObject) object);
                ((javafx.scene.control.TableView)body).getColumns().add(column);
            }
        }
    }

    private TableColumn processJsonObjectColumnName(JsonObject jsonObject){

        TableColumn column = new TableColumn();
        if(jsonObject.size()==1&&
                jsonObject.getValue((String)jsonObject.fieldNames().toArray()[0]) instanceof JsonArray){
            String name = (String)jsonObject.fieldNames().toArray()[0];
            column.setText(name);
            for(Object object:jsonObject.getJsonArray(name)){
                if(object instanceof String){
                    TableColumn tc = processStringColumnName((String) object);
                    column.getColumns().add(tc);
                }else if(object instanceof JsonObject){
                    column.getColumns().add(processJsonObjectColumnName((JsonObject)object));
                }
            }
        }else{

        }

        return column;
    }

    private TableColumn processStringColumnName(String name){
        TableColumn column = new TableColumn(name);
        column.setCellValueFactory( p -> ((TableColumn.CellDataFeatures<Item, String>)p).getValue().getProperty(name));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit( t -> {
            int index = ((TableColumn.CellEditEvent<Item, String>) t).getTablePosition().getRow();
            Item item = ((TableColumn.CellEditEvent<Item, String>) t).getTableView().getItems().get(index);
            item.setProperty(name,((TableColumn.CellEditEvent<Item, String>) t).getNewValue());
                });
        columnMap.put(name, column);
        return column;
    }

    public void setValue(JsonArray values){
        if(values==null) return;
        List vs = values.getList();
        for(int i=0;i<vs.size();i++){
            List<TableColumn> column = ((javafx.scene.control.TableView)body).getColumns();
            Item item = new Item();
            Object object = vs.get(i);
            if(object instanceof List){
                List<String> list = (List)object;
                for(int j=0;j<column.size();j++){
                    if(j>=list.size()) continue;
                    item.setProperty(column.get(j).getText(),list.get(j));
                }
            }else if(object instanceof Map){
                Map<String, String> map = (Map)object;
                for(String key:map.keySet()){
                    item.setProperty(key, map.get(key));
                }
            }

            items.add(item);
        }
    }
}

class Item{
    Map<String, StringProperty> properties = new HashMap<>();

    public StringProperty getProperty(String name){
        if(properties.get(name)==null){
            properties.put(name, new SimpleStringProperty());
        }
        return properties.get(name);
    }

    public void setProperty(String name, String value){
        if(properties.get(name)==null){
            properties.put(name, new SimpleStringProperty(value));
        }
        properties.get(name).set(value);
    }
}
