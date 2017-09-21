package com.whitewoodcity.core.bean;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "script")
public class Script {

    @XmlAttribute(name = "type")
    private String type;

    @XmlAttribute(name="link")
    private String link;

    @XmlAttribute(name = "href")
    private String href;

    @XmlAttribute(name = "version")
    private String version;

    @XmlValue
    private String script;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @Override
    public String toString() {
        return "Script{" +
                "type='" + type + '\'' +
                ", link='" + link + '\'' +
                ", href='" + href + '\'' +
                ", version='" + version + '\'' +
                ", script='" + script + '\'' +
                '}';
    }
}
