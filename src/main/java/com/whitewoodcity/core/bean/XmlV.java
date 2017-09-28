package com.whitewoodcity.core.bean;

import com.whitewoodcity.controller.TabContent;
import com.whitewoodcity.core.node.Canvas;
import com.whitewoodcity.core.node.Node;
import com.whitewoodcity.core.node.Pane;
import com.whitewoodcity.core.node.conrol.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class XmlV {
    private Json json;
    private Script script;
    private CSS css;
    private JsonFX jsonfx;

    public com.whitewoodcity.core.bean.Json getJson() {
        return json;
    }

    public void setJson(com.whitewoodcity.core.bean.Json json) {
        this.json = json;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public CSS getCss() {
        return css;
    }

    public void setCss(CSS css) {
        this.css = css;
    }

    public JsonFX getJsonfx() {
        return jsonfx;
    }

    public void setJsonfx(JsonFX jsonfx) {
        this.jsonfx = jsonfx;
    }

    public boolean isCssEmpty(){
        return css==null||css.getCss()==null||css.getCss().trim().equals("");
    }

    public Node generateNode(TabContent tabContent) throws Exception{
        Json json = getJson();

        String jsonString = json==null?"":json.getJson().trim();

        if(jsonString.startsWith("{")) return generateNodeByJsonObject(new JsonObject(jsonString), tabContent);
        else if(jsonString.startsWith("[")){
            JsonObject jsonObject = new JsonObject();
            jsonObject.put("children",new JsonArray(jsonString));
            return generateNodeByJsonObject(jsonObject,tabContent);
        }else{
            JsonObject jsonObject = new JsonObject();
            jsonObject.put("type","canvas");
            jsonObject.put("id","canvas");
            Canvas node = (Canvas) generateNodeByJsonObject(jsonObject,tabContent);
            node.widthProperty().bind(tabContent.getContainer().widthProperty());
            node.heightProperty().bind(tabContent.getContainer().heightProperty());

            return node;
        }

    }

    public Node generateNodeByJsonObject(JsonObject jsonObject, TabContent tabContent) throws Exception{
        String type = jsonObject.getString("type");
        Node node;

        switch (type==null?"pane":type.toLowerCase()){
            case "canvas":
                Canvas canvas = new Canvas();
                node = canvas;
                break;
            case "form":
                Form form = new Form(tabContent);
                form.setChildren(jsonObject.getJsonArray("children"));
                form.setAction(jsonObject.getString("action"));
                form.setMethod(jsonObject.getString("method"));
                node = form;
                break;
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
                        Node child = generateNodeByJsonObject((JsonObject)jo, tabContent);
                        if(child.getNode()!=null) pane.add(child);
                    }
                }
                node = pane;
                break;
        }

        if(jsonObject.getString("id")!=null&&tabContent.getScriptEngine()!=null){
            node.setId(jsonObject.getString("id"));
            tabContent.getScriptEngine().put(jsonObject.getString("id"), node);
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
        node.setName(jsonObject.getString("name"));
    }

}
