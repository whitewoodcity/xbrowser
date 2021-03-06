package com.whitewoodcity.core.node.conrol;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TableView extends Control{

    private Map<String, TableColumn> columnMap = new HashMap<>();
    ObservableList<Item> items = FXCollections.observableArrayList();

    public TableView(){
        if (Platform.isFxApplicationThread()) tableView();
        else Platform.runLater(this::tableView);
    }

    private void tableView(){
        body = new javafx.scene.control.TableView<Item>();
        ((javafx.scene.control.TableView)body).setItems(items);
        ((javafx.scene.control.TableView)body).setEditable(true);
        ((javafx.scene.control.TableView)body).getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        ((javafx.scene.control.TableView)body).setColumnResizePolicy((param) -> true );
        body.widthProperty().addListener( (obj, oldVal, newVal)->{
            for(TableColumn column:columnMap.values()){
                column.setPrefWidth(newVal.doubleValue()/columnMap.size());
            }
        });
    }

    public void setEditable(Boolean editable){
        if(editable!=null){
            if (Platform.isFxApplicationThread()) ((javafx.scene.control.TableView)body).setEditable(editable);
            else Platform.runLater(()->((javafx.scene.control.TableView)body).setEditable(editable));
        }
    }

    public void setHeader(JsonArray header){
        if (Platform.isFxApplicationThread()) setHeaderBase(header);
        else Platform.runLater(()->setHeaderBase(header));
    }
    private void setHeaderBase(JsonArray header){
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
            String name = jsonObject.getString("name");
            String type = jsonObject.getString("type");
            if("checkbox".equals(type)){
                column = processCheckBoxColumnName(name);
            }else if("choicebox".equals(type)||"combobox".equals(type)){
                JsonArray jsonArray = new JsonArray();
                if(jsonObject.getJsonArray("items")!=null) jsonArray.addAll(jsonObject.getJsonArray("items"));
                if(jsonObject.getJsonArray("item")!=null) jsonArray.addAll(jsonObject.getJsonArray("item"));

                if("choicebox".equals(type))
                    column = processChoiceBoxColumnName(name, jsonArray);
                else
                    column = processComboBoxColumnName(name, jsonArray);
            }else{
                column = processStringColumnName(name);
            }
        }

        return column;
    }

    private TableColumn processStringColumnName(String name){
        TableColumn column = new TableColumn(name);
        column.setCellValueFactory( p -> ((TableColumn.CellDataFeatures<Item, Object>)p).getValue().getStringProperty(name));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit( t -> {
            int index = ((TableColumn.CellEditEvent<Item, Object>) t).getTablePosition().getRow();
            Item item = ((TableColumn.CellEditEvent<Item, Object>) t).getTableView().getItems().get(index);
            item.setProperty(name,((TableColumn.CellEditEvent<Item, Object>) t).getNewValue());
                });
        columnMap.put(name, column);
        return column;
    }

    private TableColumn processCheckBoxColumnName(String name){
        TableColumn column = new TableColumn(name);
        column.setCellValueFactory( p -> ((TableColumn.CellDataFeatures<Item, Boolean>)p).getValue().getBooleanProperty(name));
        column.setCellFactory(CheckBoxTableCell.forTableColumn(column));
        column.setOnEditCommit( t -> {
            int index = ((TableColumn.CellEditEvent<Item, Boolean>) t).getTablePosition().getRow();
            Item item = ((TableColumn.CellEditEvent<Item, Boolean>) t).getTableView().getItems().get(index);
            item.setProperty(name,((TableColumn.CellEditEvent<Item, Boolean>) t).getNewValue());
        });
        columnMap.put(name, column);
        return column;
    }

    private TableColumn processChoiceBoxColumnName(String name, JsonArray items){
        TableColumn column = new TableColumn(name);
        column.setCellValueFactory( p -> ((TableColumn.CellDataFeatures<Item, Object>)p).getValue().getStringProperty(name));
        ObservableList list = FXCollections.observableArrayList();
        if(items!=null) list.addAll(items.getList());
        column.setCellFactory(ChoiceBoxTableCell.forTableColumn(list));
        column.setOnEditCommit( t -> {
            int index = ((TableColumn.CellEditEvent<Item, Object>) t).getTablePosition().getRow();
            Item item = ((TableColumn.CellEditEvent<Item, Object>) t).getTableView().getItems().get(index);
            item.setProperty(name,((TableColumn.CellEditEvent<Item, Object>) t).getNewValue());
        });
        columnMap.put(name, column);
        return column;
    }

    private TableColumn processComboBoxColumnName(String name, JsonArray items){
        TableColumn column = new TableColumn(name);
        column.setCellValueFactory( p -> ((TableColumn.CellDataFeatures<Item, Object>)p).getValue().getStringProperty(name));
        ObservableList list = FXCollections.observableArrayList();
        if(items!=null) list.addAll(items.getList());
        column.setCellFactory(ComboBoxTableCell.forTableColumn(list));
        column.setOnEditCommit( t -> {
            int index = ((TableColumn.CellEditEvent<Item, Object>) t).getTablePosition().getRow();
            Item item = ((TableColumn.CellEditEvent<Item, Object>) t).getTableView().getItems().get(index);
            item.setProperty(name,((TableColumn.CellEditEvent<Item, Object>) t).getNewValue());
        });
        columnMap.put(name, column);
        return column;
    }

    public void setValue(JsonArray values){
        if (Platform.isFxApplicationThread()) setValueBase(values);
        else Platform.runLater(()->setValueBase(values));
    }

    private void setValueBase(JsonArray values){
        if(values==null) return;
        List vs = values.getList();
        List<TableColumn> leafColumns = ((javafx.scene.control.TableView)body).getVisibleLeafColumns();
        for(int i=0;i<vs.size();i++){
            Item item = new Item();
            Object object = vs.get(i);
            if(object instanceof List){
                List list = (List)object;
                for(int j=0;j<leafColumns.size();j++){
                    if(j>=list.size()) continue;
                    item.setProperty(leafColumns.get(j).getText(),list.get(j));
                }
            }else if(object instanceof Map){
                Map<String, Object> map = (Map)object;
                for(String key:map.keySet()){
                    item.setProperty(key, map.get(key));
//                    if(map.get(key) instanceof Boolean)
//                        item.setProperty(key, (Boolean)map.get(key));
//                    else
//                        item.setProperty(key, map.get(key));
                }
            }

            items.add(item);
        }
    }

    public JsonArray getValue() throws InterruptedException,ExecutionException{
        if (Platform.isFxApplicationThread()) {
            return getValueBase();
        }else{
            final FutureTask<JsonArray> task = new FutureTask<>(this::getValueBase);
            Platform.runLater(task);
            return task.get();
        }
    }

    private JsonArray getValueBase(){
        JsonArray jsonArray = new JsonArray();
        for(Item item:items){
            JsonObject jsonObject = new JsonObject();
            Map<String, Property> map = item.properties;
            for(String key:map.keySet()){
                jsonObject.put(key,map.get(key).getValue());
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}

class Item{
    Map<String, Property> properties = new HashMap<>();

    public StringProperty getStringProperty(String name){
        if(properties.get(name)==null){
            properties.put(name, new SimpleStringProperty());
        }
        return (StringProperty)properties.get(name);
    }

    public BooleanProperty getBooleanProperty(String name){
        if(properties.get(name)==null){
            properties.put(name, new SimpleBooleanProperty());
        }
        if(!(properties.get(name) instanceof BooleanProperty)){
            properties.put(name, new SimpleBooleanProperty());
        }
        return (BooleanProperty)properties.get(name);
    }

    public void setProperty(String name, Object value){
        if(value instanceof Boolean) {
            setProperty(name, (Boolean) value);
            return;
        }

        if(properties.get(name)==null){
            properties.put(name, new SimpleStringProperty(value.toString()));
        }

        if(value!=null){
            ((StringProperty) properties.get(name)).set(value.toString());
        }
    }

    public void setProperty(String name, Boolean value){
        if(properties.get(name)==null){
            properties.put(name, new SimpleBooleanProperty());
        }
        if(properties.get(name) instanceof BooleanProperty){
            ((BooleanProperty)properties.get(name)).set(value);
        }
    }

}
