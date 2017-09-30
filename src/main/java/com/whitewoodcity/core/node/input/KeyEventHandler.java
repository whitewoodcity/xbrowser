package com.whitewoodcity.core.node.input;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyEventHandler implements EventHandler<KeyEvent> {

    private KeyPressedOrReleasedHandler press;
    private KeyPressedOrReleasedHandler release;

    public KeyEventHandler(){
        press = (code) -> {};
        release = (code) -> {};
    }

    @Override
    public void handle(KeyEvent event) {
        String code = event.getCode().getName().toLowerCase();

        if(event.getEventType()==KeyEvent.KEY_PRESSED){
            press.handle(code);
        }else if(event.getEventType()==KeyEvent.KEY_RELEASED){
            release.handle(code);
        }
    }

    public void setPress(KeyPressedOrReleasedHandler press) {
        this.press = press;
    }

    public void setRelease(KeyPressedOrReleasedHandler release) {
        this.release = release;
    }
}
