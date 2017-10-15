package com.whitewoodcity.core.node;

import javafx.application.Platform;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class RunLater <T,R> {
    private RunLaterHandler<T,R> handler;

    public RunLater(RunLaterHandler<T,R> handler) {
        this.handler = handler;
    }

    public R run(T t) throws InterruptedException, ExecutionException {
        if (Platform.isFxApplicationThread()) {
            return handler.handle(t);
        }
        final FutureTask<R> task = new FutureTask<>(() -> handler.handle(t));
        Platform.runLater(task);
        return task.get();
    }
}
