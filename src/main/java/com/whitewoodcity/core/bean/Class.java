package com.whitewoodcity.core.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Class {

    @JacksonXmlProperty(isAttribute = true)
    private String url;
    @JacksonXmlProperty(isAttribute = true)
    private String name;
    @JacksonXmlProperty(isAttribute = true)
    private String function;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}
