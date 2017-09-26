package com.whitewoodcity.core.bean;

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
}
