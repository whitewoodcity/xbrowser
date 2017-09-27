package com.whitewoodcity.core.node;

import javafx.application.Platform;

public class AnimationTimer extends javafx.animation.AnimationTimer{

    Handler<Long> handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void handle(long now) {
        Platform.runLater(()->
            handler.handle(now)
        );
    }
}
