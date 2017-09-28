package com.whitewoodcity.core.node;

import javafx.application.Platform;

public class AnimationTimer extends javafx.animation.AnimationTimer{

    ActionHandler<Long> action;

    public void setAction(ActionHandler<Long> action) {
        this.action = action;
    }

    @Override
    public void handle(long now) {
        Platform.runLater(()->
            action.handle(now)
        );
    }
}
