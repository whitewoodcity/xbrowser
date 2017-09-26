package com.whitewoodcity.core.parse;

import com.whitewoodcity.Main;
import com.whitewoodcity.util.JarRes;
import com.whitewoodcity.util.Res;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import java.net.URL;
import java.net.URLClassLoader;

public class ScriptFactory {

    public static ScriptEngine loadJRubyScript(){
        URL jrubyJar= Res.getExternalJar(JarRes.JRUBY);
        if(jrubyJar==null){
            return null;
        }
        URLClassLoader classLoader=new URLClassLoader(new URL[]{jrubyJar},Thread.currentThread().getContextClassLoader());
        try {
            Class<?> engineFactory=classLoader.loadClass("org.jruby.embed.jsr223.JRubyEngineFactory");
            ScriptEngineFactory factory= (ScriptEngineFactory) engineFactory.newInstance();
            Main.scriptEngineManager.registerEngineName("jruby",factory);
            return Main.scriptEngineManager.getEngineByName("jruby");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }


}
