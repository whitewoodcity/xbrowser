package com.whitewoodcity.core.bean;

import java.util.ArrayList;
import java.util.List;

public class VXml {

    private List<CSS> csses;

    private List<Script> scripts;

    private FXml fXml;

    public List<CSS> getCsses() {
        return csses==null?new ArrayList<>():csses;
    }

    public void setCsses(List<CSS> csses) {
        this.csses = csses;
    }

    public List<Script> getScripts() {
        return scripts;
    }

    public void setScripts(List<Script> scripts) {
        this.scripts = scripts;
    }

    public FXml getfXml() {
        return fXml;
    }

    public void setfXml(FXml fXml) {
        this.fXml = fXml;
    }

}
