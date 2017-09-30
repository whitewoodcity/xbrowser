package com.whitewoodcity.core.node.input;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MouseEventHandler implements EventHandler<MouseEvent>{

    private MouseMovedHandler move;
    private MousePressedOrReleasedHandler press;
    private MousePressedOrReleasedHandler release;
    private MousePressedOrReleasedHandler rightPress;
    private MousePressedOrReleasedHandler rightRelease;

    public MouseEventHandler() {
        move = (x,y) -> {};
        press = (x,y) -> {};
        release = (x,y) -> {};
        rightPress = (x,y) -> {};
        rightRelease = (x,y) -> {};
    }

    @Override
    public void handle(MouseEvent event) {
        if(event.getEventType()==MouseEvent.MOUSE_MOVED){
            move.handle(event.getX(),event.getY());
        }else if(event.getEventType()==MouseEvent.MOUSE_PRESSED){
            if(event.getButton()==MouseButton.PRIMARY){
                press.handle(event.getX(),event.getY());
            }else if(event.getButton()==MouseButton.SECONDARY){
                rightPress.handle(event.getX(),event.getY());
            }
        }else if(event.getEventType()==MouseEvent.MOUSE_RELEASED){
            if(event.getButton()==MouseButton.PRIMARY){
                release.handle(event.getX(),event.getY());
            }else if(event.getButton()==MouseButton.SECONDARY){
                rightRelease.handle(event.getX(),event.getY());
            }
        }
    }

    public void setMove(MouseMovedHandler move) {
        this.move = move;
    }

    public void setPress(MousePressedOrReleasedHandler press) {
        this.press = press;
    }

    public void setRelease(MousePressedOrReleasedHandler release) {
        this.release = release;
    }

    public void setRightPress(MousePressedOrReleasedHandler rightPress) {
        this.rightPress = rightPress;
    }

    public void setRight_press(MousePressedOrReleasedHandler rightPress) {
        setRightPress(rightPress);
    }

    public void setRightpress(MousePressedOrReleasedHandler rightPress) {
        setRightPress(rightPress);
    }

    public void setRightRelease(MousePressedOrReleasedHandler rightRelease) {
        this.rightRelease = rightRelease;
    }

    public void setRight_release(MousePressedOrReleasedHandler rightRelease) {
        setRightRelease(rightRelease);
    }
    public void setRightrelease(MousePressedOrReleasedHandler rightRelease) {
        setRightRelease(rightRelease);
    }
}
