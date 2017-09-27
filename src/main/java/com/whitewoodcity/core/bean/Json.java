package com.whitewoodcity.core.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import com.whitewoodcity.controller.TabContent;
import com.whitewoodcity.core.node.Canvas;
import com.whitewoodcity.core.node.conrol.*;
import com.whitewoodcity.core.node.Node;
import com.whitewoodcity.core.node.Pane;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Json {

    @JacksonXmlProperty(isAttribute = true)
    private String href;

    @JacksonXmlText
    private String json;

    public Json(String json) {
        this.json = json;
    }

    public String getJson() {
        return json==null?"":json;
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
}
