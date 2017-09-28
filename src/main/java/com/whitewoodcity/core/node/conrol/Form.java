package com.whitewoodcity.core.node.conrol;

import com.whitewoodcity.controller.TabContent;
import io.vertx.core.json.JsonArray;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Form extends Control{

    StringProperty id = new SimpleStringProperty();
    TabContent content;
    JsonArray children = new JsonArray();
    String method;
    String action;

    public Form(TabContent content){
        this.content = content;
    }

    public JsonArray getChildren() {
        return children;
    }

    public void setChildren(JsonArray children) {
        this.children = children;
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

    public void submit(){
        if(children==null || children.size() == 0) return;
        String[] ids = new String[children.size()];
        for(int i = 0;i<children.size();i++){
            ids[i] = children.getValue(i).toString();
        }
        content.submit(ids,method,action);
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
}
