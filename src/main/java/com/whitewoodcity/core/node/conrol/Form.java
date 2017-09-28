package com.whitewoodcity.core.node.conrol;

import com.whitewoodcity.controller.TabContent;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonArray;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Form extends Control{

    MultiMap form = MultiMap.caseInsensitiveMultiMap();

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

        for(int i = 0;i<children.size();i++){
            String id = children.getValue(i).toString();

            Object object = app.getScriptEngine().get(id);
            if(object!=null && object instanceof Control){
                Control control = (Control)object;
                if(control.getName()==null || control.getName().isEmpty())
                    continue;
                form.set(control.getName(),control.getValue());
            }
        }
        app.submit(form,method,action);
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
        form.set(name,value);
    }
    public String get(String name){
        for(int i=0;i<children.size();i++){
            String id = children.getValue(i).toString();
            Object object = app.getScriptEngine().get(id);
            if(object!=null && object instanceof Control){
                Control control = (Control)object;
                if(control.getName().equals(name))
                    return control.getValue();
            }
        }

        return form.get(name);
    }
    public void remove(String id){
        children.remove(id);
    }
    public void add(String id){
        children.add(id);
    }
    public void clear(){
        form.clear();
        children.clear();
    }
}
