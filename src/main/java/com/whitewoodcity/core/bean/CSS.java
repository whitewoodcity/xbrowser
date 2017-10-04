package com.whitewoodcity.core.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class CSS {

    @JacksonXmlProperty(isAttribute = true)
    private String href;

    @JacksonXmlText
    private String css;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    @Override
    public String toString() {
        return "CSS{" +
                "href='" + href + '\'' +
                ", css='" + css + '\'' +
                '}';
    }
}
