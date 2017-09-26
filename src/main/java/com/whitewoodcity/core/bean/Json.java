package com.whitewoodcity.core.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import com.whitewoodcity.core.node.conrol.Button;
import com.whitewoodcity.core.node.conrol.Control;
import com.whitewoodcity.core.node.conrol.Label;
import com.whitewoodcity.core.node.Node;
import com.whitewoodcity.core.node.Pane;
import com.whitewoodcity.core.node.conrol.TextField;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import javax.script.ScriptEngine;

public class Json {

    @JacksonXmlProperty(isAttribute = true)
    private String href;

    @JacksonXmlText
    private String json;

    public Json(String json) {
        this.json = json;
    }

    public String getJson() {
        return json;
    }

    public JsonObject getJsonObject(){
        return new JsonObject(json);
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "Json{" +
                "href='" + href + '\'' +
                ", json='" + json + '\'' +
                '}';
    }

    public Node generateNode(ScriptEngine engine) throws Exception{
        return generateNodeByJsonObject(this.getJsonObject(), engine);
    }

    public Node generateNodeByJsonObject(JsonObject jsonObject, ScriptEngine engine) throws Exception{
        String type = jsonObject.getString("type");
        Node node;

        Double x, y, width, height;

        switch (type==null?"pane":type.toLowerCase()){
            case "textfield":
                TextField textField = new TextField();
                decorateControl(textField,jsonObject);
                textField.setText(jsonObject.getString("text"));
                node = textField;
                break;
            case "label":
                Label label = new Label();
                decorateControl(label,jsonObject);
                label.setText(jsonObject.getString("text"));
                node = label;
                break;
            case "button":
                Button button = new Button();
                decorateControl(button,jsonObject);
                button.setText(jsonObject.getString("text"));
                node = button;
                break;
            default:
                Pane pane = new Pane();
                JsonArray jsonArray = jsonObject.getJsonArray("children");
                if(jsonArray!=null){
                    for(Object jo:jsonArray){
                        Node child = generateNodeByJsonObject((JsonObject)jo, engine);
                        pane.add(child);
                    }
                }
                node = pane;
                break;
        }

        if(jsonObject.getString("id")!=null&&engine!=null){
            node.setId(jsonObject.getString("id"));
            engine.put(jsonObject.getString("id"), node);
        }

        return node;
    }

    public void decorateControl(Control node, JsonObject jsonObject){
        Double x, y, width, height;
        x = jsonObject.getDouble("x");
        y = jsonObject.getDouble("y");
        width = jsonObject.getDouble("width");
        height = jsonObject.getDouble("height");
        node.setX(x==null?0:x);
        node.setY(y==null?0:y);
        if(width!=null) node.setWidth(width);
        if(height!=null) node.setHeight(height);
    }

}
