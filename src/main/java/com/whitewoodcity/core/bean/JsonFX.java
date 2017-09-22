package com.whitewoodcity.core.bean;

public class JsonFX extends Json{

    public JsonFX(String jsonfx) {
        super(jsonfx);
    }

    public String getJsonfx() {
        return getJson();
    }

    public void setJsonfx(String jsonfx) {
        this.setJson(jsonfx);
    }
}
