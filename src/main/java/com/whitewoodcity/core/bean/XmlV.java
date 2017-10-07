package com.whitewoodcity.core.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.whitewoodcity.controller.TabContent;
import com.whitewoodcity.core.node.canvas.Canvas;
import com.whitewoodcity.core.node.Node;
import com.whitewoodcity.core.node.Pane;
import com.whitewoodcity.core.node.chart.*;
import com.whitewoodcity.core.node.conrol.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JacksonXmlRootElement(localName = "xmlv")
public class XmlV {

    private Preload preload;
    private Json json;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "css")
    private CSS[] csses;
    private JsonFX jsonfx;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "class")
    private Class[] classes;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "script")
    private Script[] scripts;

    public com.whitewoodcity.core.bean.Json getJson() {
        return json;
    }

    public void setJson(com.whitewoodcity.core.bean.Json json) {
        this.json = json;
    }

    public Script[] getScripts() {
        return scripts;
    }

    public void setScripts(Script[] scripts) {
        this.scripts = scripts;
    }

    public CSS[] getCsses() {
        return csses;
    }

    public void setCsses(CSS[] csses) {
        this.csses = csses;
    }

    public JsonFX getJsonfx() {
        return jsonfx;
    }

    public void setJsonfx(JsonFX jsonfx) {
        this.jsonfx = jsonfx;
    }

    public Preload getPreload() {
        return preload;
    }

    public void setPreload(Preload preload) {
        this.preload = preload;
    }

    public Class[] getClasses() {
        return classes;
    }

    public void setClasses(Class[] classes) {
        this.classes = classes;
    }

    public List<String> generateResources(){
        List<String> resources = new ArrayList<>();
        if(getPreload()!=null && getPreload().getPreload()!=null &&
                !getPreload().getPreload().replace("\n","").trim().equals("")){
            String preload = getPreload().getPreload().replace("\n","").trim();
            String[] elements = preload.split(";");
            for(String element:elements){
                resources.add(element);
            }
        }
        return resources;
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

    public Node generateNodeByJsonObject(JsonObject jsonObject, TabContent app) throws Exception{
        String type = jsonObject.getString("type");
        Node node;

        JsonArray jsonArray = new JsonArray();

        switch (type==null?"pane":type.toLowerCase()){
            case "canvas":
                Canvas canvas = new Canvas();
                node = canvas;
                break;
            case "form":
                Form form = new Form();
                form.setApp(app);
                form.setChildren(jsonObject.getJsonArray("children"));
                form.setAction(jsonObject.getString("action"));
                form.setMethod(jsonObject.getString("method"));
                node = form;
                break;
            case "image":
            case "img":
            case "imageview":
                ImageView imageView = new ImageView(jsonObject.getString("url"));
                imageView.setImage(app.getPreload().get(jsonObject.getString("image")));
                decorateControl(imageView,jsonObject);
                node = imageView;
                break;
            case "table":
            case "tableview":
                TableView tableView = new TableView();
                jsonArray.clear();
                if(jsonObject.getJsonArray("header")!=null) jsonArray.addAll(jsonObject.getJsonArray("header"));
                if(jsonObject.getJsonArray("headers")!=null) jsonArray.addAll(jsonObject.getJsonArray("headers"));
                if(jsonObject.getJsonArray("column")!=null) jsonArray.addAll(jsonObject.getJsonArray("column"));
                if(jsonObject.getJsonArray("columns")!=null) jsonArray.addAll(jsonObject.getJsonArray("columns"));
                tableView.setHeader(jsonArray);
                jsonArray.clear();
                if(jsonObject.getJsonArray("value")!=null) jsonArray.addAll(jsonObject.getJsonArray("value"));
                if(jsonObject.getJsonArray("values")!=null) jsonArray.addAll(jsonObject.getJsonArray("values"));
                tableView.setValue(jsonArray);
                tableView.setEditable(jsonObject.getBoolean("editable"));
                decorateControl(tableView,jsonObject);
                node = tableView;
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
            case "hyperlink":
                Hyperlink hyperlink = new Hyperlink();
                hyperlink.setApp(app);
                decorateControl(hyperlink,jsonObject);
                hyperlink.setText(jsonObject.getString("text"));
                hyperlink.setValue(jsonObject.getString("value"));
                node = hyperlink;
                break;
            case "button":
                Button button = new Button();
                decorateControl(button,jsonObject);
                button.setText(jsonObject.getString("text"));
                node = button;
                break;
            case "choicebox":
                ChoiceBox choiceBox = new ChoiceBox();
                decorateControl(choiceBox,jsonObject);
                jsonArray.clear();
                if(jsonObject.getJsonArray("item")!=null) jsonArray.addAll(jsonObject.getJsonArray("item"));
                if(jsonObject.getJsonArray("items")!=null) jsonArray.addAll(jsonObject.getJsonArray("items"));
                choiceBox.setItems(jsonArray);
                choiceBox.setValue(jsonObject.getString("value"));
                node = choiceBox;
                break;
            case "piechart":
                PieChart pieChart = new PieChart();
                decorateChart(pieChart,jsonObject);
                pieChart.setData(jsonObject.getJsonObject("data"));
                node = pieChart;
                break;
            case "linechart":
            case "barchart":
            case "scatterchart":
            case "areachart":
                AxisType xAxisType = AxisType.NUMBER;
                if(jsonObject.getValue("xaxis")!=null&&
                        jsonObject.getValue("xaxis") instanceof JsonArray &&
                        jsonObject.getJsonArray("xaxis").size()>0){
                    if(!(jsonObject.getJsonArray("xaxis").getValue(0) instanceof Number)){
                        xAxisType = AxisType.CATEGORY;
                    }
                }
                AxisType yAxisType = AxisType.NUMBER;
                if(jsonObject.getValue("data")!=null&&
                        jsonObject.getValue("data") instanceof JsonObject &&
                        jsonObject.getJsonObject("data").fieldNames().size()>0){
                    String key = (String) jsonObject.getJsonObject("data").fieldNames().toArray()[0];
                    if(jsonObject.getJsonObject("data").getValue(key) instanceof JsonArray &&
                            jsonObject.getJsonObject("data").getJsonArray(key).size()>0){
                        if(!(jsonObject.getJsonObject("data").getJsonArray(key).getValue(0) instanceof Number)
                                && !(jsonObject.getJsonObject("data").getJsonArray(key).getValue(0) instanceof JsonObject)){
                            yAxisType = AxisType.CATEGORY;
                        }else if((jsonObject.getJsonObject("data").getJsonArray(key).getValue(0) instanceof JsonObject &&
                                !(jsonObject.getJsonObject("data").getJsonArray(key).getJsonObject(0).getValue("y") instanceof Number))){
                            yAxisType = AxisType.CATEGORY;
                        }
                    }
                }
                XYChart xyChart = new XYChart(type, xAxisType, yAxisType);
                decorateChart(xyChart,jsonObject);
                xyChart.setXScale(jsonObject.getJsonArray("xaxis"));
                xyChart.setXLabel(jsonObject.getString("xlabel"));
                xyChart.setYLabel(jsonObject.getString("ylabel"));
                xyChart.setData(jsonObject.getJsonObject("data"));
                node = xyChart;
                break;
            case "bubblechart":
                XYChart bubbleChart = new XYChart(type, AxisType.NUMBER, AxisType.NUMBER);
                decorateChart(bubbleChart,jsonObject);
                bubbleChart.setXLabel(jsonObject.getString("xlabel"));
                bubbleChart.setYLabel(jsonObject.getString("ylabel"));
                bubbleChart.setData(jsonObject.getJsonObject("data"));
                node = bubbleChart;
                break;
            default:
                Pane pane = new Pane();
                jsonArray = jsonObject.getJsonArray("children");
                if(jsonArray!=null){
                    for(Object jo:jsonArray){
                        Node child = generateNodeByJsonObject((JsonObject)jo, app);
                        if(child.getNode()!=null) pane.add(child);
                    }
                }
                node = pane;
                break;
        }

        if(jsonObject.getString("id")!=null){
            node.setId(jsonObject.getString("id"));
            app.getContext().put(jsonObject.getString("id"), node);
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

    public void decorateChart(Chart node, JsonObject jsonObject){
        Double x, y, width, height;
        x = jsonObject.getDouble("x");
        y = jsonObject.getDouble("y");
        width = jsonObject.getDouble("width");
        height = jsonObject.getDouble("height");
        node.setX(x==null?0:x);
        node.setY(y==null?0:y);
        if(width!=null) node.setWidth(width);
        if(height!=null) node.setHeight(height);
        node.setTitle(jsonObject.getString("title"));
        node.setTitleSide(jsonObject.getString("titleside"));
        node.setLegendSide(jsonObject.getString("legendside"));
    }

}
