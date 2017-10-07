package com.whitewoodcity.core.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Class {

    @JacksonXmlProperty(isAttribute = true)
    private String href;


    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
