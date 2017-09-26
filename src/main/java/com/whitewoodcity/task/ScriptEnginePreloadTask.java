package com.whitewoodcity.task;

import com.whitewoodcity.Main;
import javafx.concurrent.Task;

import javax.script.ScriptEngine;

public class ScriptEnginePreloadTask extends Task<Integer> {
    @Override protected Integer call() throws Exception {
        ScriptEngine engine = Main.scriptEngineManager.getEngineByName("ruby");
        engine.eval("puts('ruby')");
        engine = Main.scriptEngineManager.getEngineByName("javascript");
        engine.eval("print('javascript')");
        engine = Main.scriptEngineManager.getEngineByName("Groovy");
        engine.eval("println('groovy')");
        return 0;
    }
}
