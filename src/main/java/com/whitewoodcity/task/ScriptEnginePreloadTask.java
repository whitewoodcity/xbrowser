package com.whitewoodcity.task;

import javafx.concurrent.Task;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScriptEnginePreloadTask extends Task<Integer> {
    @Override protected Integer call() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        engine.eval("print('javascript')");
        engine = manager.getEngineByName("ruby");
        engine.eval("puts('ruby')");
        engine = manager.getEngineByName("Groovy");
        engine.eval("println('groovy')");
        return 0;
    }
}
