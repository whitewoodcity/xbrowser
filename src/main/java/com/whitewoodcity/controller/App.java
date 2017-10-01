package com.whitewoodcity.controller;

import com.whitewoodcity.core.node.AnimationTimer;
import com.whitewoodcity.core.node.input.KeyEventHandler;
import com.whitewoodcity.core.node.input.MouseEventHandler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class App {
    protected MediaPlayer mediaPlayer;
    protected AnimationTimer timer;
    protected MouseEventHandler mouseEventHandler = new MouseEventHandler();
    protected KeyEventHandler keyEventHandler = new KeyEventHandler();

    public MediaPlayer play(Media media){
        return play(media, Integer.MAX_VALUE);
    }

    public MediaPlayer play(Media media, int cycle){
        return play(media,cycle,1);
    }

    public MediaPlayer play(Media media, int cycle, double volume){
        if (mediaPlayer != null) mediaPlayer.dispose();
        if (media == null) return null;
        if (cycle <= 0 ) return null;
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(volume);
        mediaPlayer.setCycleCount(cycle);

        mediaPlayer.setOnError(() -> play(media, mediaPlayer.getCycleCount() - mediaPlayer.getCurrentCount(), mediaPlayer.getVolume()));
        mediaPlayer.setOnStopped(() -> play(media, mediaPlayer.getCycleCount() - mediaPlayer.getCurrentCount(), mediaPlayer.getVolume()));
        mediaPlayer.setOnEndOfMedia(() -> play(media, mediaPlayer.getCycleCount() - mediaPlayer.getCurrentCount(), mediaPlayer.getVolume()));

        mediaPlayer.play();

        return mediaPlayer;
    }

    public AnimationTimer timer() {
        return timer = new AnimationTimer();
    }

    public AnimationTimer getTimer() {
        return timer();
    }

    public void dispose(){
        if (timer != null) timer.stop();
        if (mediaPlayer !=null) mediaPlayer.dispose();
    }

    public void focus(com.whitewoodcity.core.node.Node node){
        node.getNode().requestFocus();
    }
}
