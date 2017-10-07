package com.whitewoodcity.core.node.conrol;

import com.whitewoodcity.controller.TabContent;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Map;

public class Form extends Control{

    JsonObject jsonObject = new JsonObject();
    StringProperty id = new SimpleStringProperty();
    TabContent app;
    JsonArray children = new JsonArray();
    String method;
    String action;
    FormHandler handler;

    public Form(){//TabContent content
//        this.content = content;
        handler = () -> false;
    }

    public void setApp(TabContent app) {
        this.app = app;
    }

    public JsonArray getChildren() {
        return children;
    }

    public void setChildren(JsonArray children) {
        this.children.clear();
        if(children!=null)
            this.children.addAll(children);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setAction(FormHandler action){
        this.handler = action;
    }

    public void submit(){
        if(handler.handle()) return;

        MultiMap form = MultiMap.caseInsensitiveMultiMap();

        for(int i = 0;i<children.size();i++){
            String id = children.getValue(i).toString();

            Object object = app.getContext().get(id);
            if(object!=null && object instanceof Control){
                Control control = (Control)object;
                if(control.getName()==null || control.getName().isEmpty())
                    continue;
                form.set(control.getName(),control.getValue().toString());
            }
        }
        for(String key:jsonObject.fieldNames()){
            form.add(key,jsonObject.getValue(key).toString());
        }

        app.submit(form,method,action);
    }

    public void send(){
        if(handler.handle()) return;

        for(int i = 0;i<children.size();i++){
            String id = children.getValue(i).toString();

            Object object = app.getContext().get(id);

            if(object!=null && object instanceof Control){
                Control control = (Control)object;
                if(control.getName()==null || control.getName().isEmpty())
                    continue;
                jsonObject.put(control.getName(),control.getValue());
            }
        }

        app.send(jsonObject,method,action);
    }

    @Override
    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void set(String name, String value){
        jsonObject.put(name,value);
    }
    public Object get(String name){
        for(int i=0;i<children.size();i++){
            String id = children.getValue(i).toString();
            Object object = app.getContext().get(id);
            if(object!=null && object instanceof Control){
                Control control = (Control)object;
                if(control.getName().equals(name))
                    return control.getValue();
            }
        }

        return jsonObject.getValue(name).toString();
    }
    public void remove(String id){
        children.remove(id);
    }
    public void add(String id){
        children.add(id);
    }
    public void clear(){
        jsonObject.clear();
        children.clear();
    }
}
