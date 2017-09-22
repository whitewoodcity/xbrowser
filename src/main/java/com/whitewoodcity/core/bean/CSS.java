package com.whitewoodcity.core.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "css")
public class CSS {

    @XmlAttribute(name = "href")
    @JacksonXmlProperty(isAttribute = true)
    private String href;

    @XmlValue
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
