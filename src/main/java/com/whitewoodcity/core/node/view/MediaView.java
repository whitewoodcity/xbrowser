package com.whitewoodcity.core.node.view;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MediaView extends View {
    javafx.scene.media.MediaView body;

    public MediaView() {
        if (Platform.isFxApplicationThread()) body = new javafx.scene.media.MediaView();
        else Platform.runLater(() -> body = new javafx.scene.media.MediaView());
    }

    public MediaView(String url) {
        if (url == null) {
            if (Platform.isFxApplicationThread()) body = new javafx.scene.media.MediaView();
            else Platform.runLater(() -> body = new javafx.scene.media.MediaView());
        } else {
            MediaPlayer mediaPlayer = new MediaPlayer(new Media(url));
            mediaPlayer.setAutoPlay(true);
            if (Platform.isFxApplicationThread())
                body = new javafx.scene.media.MediaView(mediaPlayer);
            else Platform.runLater(()-> body = new javafx.scene.media.MediaView(mediaPlayer));
        }
    }

    public DoubleProperty widthProperty() throws InterruptedException, ExecutionException {

        if (Platform.isFxApplicationThread()) {
            return body.fitWidthProperty();
        }
        final FutureTask<DoubleProperty> task = new FutureTask<>(() -> body.fitWidthProperty());
        Platform.runLater(task);
        return task.get();
    }

    public DoubleProperty heightProperty() throws InterruptedException, ExecutionException {

        if (Platform.isFxApplicationThread()) {
            return body.fitHeightProperty();
        }
        final FutureTask<DoubleProperty> task = new FutureTask<>(() -> body.fitHeightProperty());
        Platform.runLater(task);
        return task.get();
    }

    public void setWidth(double width) {
        if (Platform.isFxApplicationThread()) {
            body.setFitWidth(width);
        } else Platform.runLater(() -> body.setFitWidth(width));
    }

    public double getWidth() throws InterruptedException, ExecutionException {
        if (Platform.isFxApplicationThread()) {
            return body.getFitWidth();
        }
        final FutureTask<Double> task = new FutureTask<>(() -> body.getFitWidth());
        Platform.runLater(task);
        return task.get();
    }

    public void setHeight(double height) {
        if (Platform.isFxApplicationThread()) {
            body.setFitHeight(height);
        } else Platform.runLater(() -> body.setFitHeight(height));
    }

    public double getHeight() throws InterruptedException, ExecutionException {
        if (Platform.isFxApplicationThread()) {
            return body.getFitHeight();
        }
        final FutureTask<Double> task = new FutureTask<>(() -> body.getFitHeight());
        Platform.runLater(task);
        return task.get();
    }

    @Override
    public javafx.scene.Node getNode() {
        return body;
    }

    public void setMedia(Object media) {
        if (media != null && media instanceof Media) {
            if (Platform.isFxApplicationThread()) {
                body.setMediaPlayer(new MediaPlayer((Media) media));
            } else Platform.runLater(() -> body.setMediaPlayer(new MediaPlayer((Media) media)));
        }
    }

    public void pause() {
        if(body!=null&&body.getMediaPlayer()!=null){
            if(Platform.isFxApplicationThread()) body.getMediaPlayer().pause();
            else Platform.runLater(() -> body.getMediaPlayer().pause());
        }
    }

    public void play() {
        if(body!=null&&body.getMediaPlayer()!=null){
            if(Platform.isFxApplicationThread()) body.getMediaPlayer().play();
            else Platform.runLater(() -> body.getMediaPlayer().play());
        }
    }

    public void stop() {
        if(body!=null&&body.getMediaPlayer()!=null){
            if(Platform.isFxApplicationThread()) body.getMediaPlayer().stop();
            else Platform.runLater(() -> body.getMediaPlayer().stop());
        }
    }

    public void dispose() {
        if(body!=null&&body.getMediaPlayer()!=null){
            if(Platform.isFxApplicationThread()) body.getMediaPlayer().dispose();
            else Platform.runLater(() -> body.getMediaPlayer().dispose());
        }
    }

    public void setPaused(Runnable value){
        paused(value);
    }
    public void paused(Runnable value) {
        if(body!=null&&body.getMediaPlayer()!=null){
            if(Platform.isFxApplicationThread()) body.getMediaPlayer().setOnPaused(value);
            else Platform.runLater(() -> body.getMediaPlayer().setOnPaused(value));
        }
    }

    public void setStopped(Runnable value){
        stopped(value);
    }
    public void stopped(Runnable value) {
        if(body!=null&&body.getMediaPlayer()!=null){
            if(Platform.isFxApplicationThread()) body.getMediaPlayer().setOnStopped(value);
            else Platform.runLater(() -> body.getMediaPlayer().setOnStopped(value));
        }
    }

    public void setPlaying(Runnable value){
        playing(value);
    }
    public void playing(Runnable value) {
        if(body!=null&&body.getMediaPlayer()!=null){
            if(Platform.isFxApplicationThread()) body.getMediaPlayer().setOnPlaying(value);
            else Platform.runLater(() -> body.getMediaPlayer().setOnPlaying(value));
        }
    }

    public void setEnd(Runnable value){
        end(value);
    }
    public void end(Runnable value) {
        if(body!=null&&body.getMediaPlayer()!=null){
            if(Platform.isFxApplicationThread()) body.getMediaPlayer().setOnEndOfMedia(value);
            else Platform.runLater(() -> body.getMediaPlayer().setOnEndOfMedia(value));
        }
    }

    public void setError(Runnable value){
        error(value);
    }
    public void error(Runnable value) {
        if(body!=null&&body.getMediaPlayer()!=null){
            if(Platform.isFxApplicationThread()) body.getMediaPlayer().setOnError(value);
            else Platform.runLater(() -> body.getMediaPlayer().setOnError(value));
        }
    }

    public void setHalted(Runnable value){
        halted(value);
    }
    public void halted(Runnable value) {
        if(body!=null&&body.getMediaPlayer()!=null){
            if(Platform.isFxApplicationThread()) body.getMediaPlayer().setOnHalted(value);
            else Platform.runLater(() -> body.getMediaPlayer().setOnHalted(value));
        }
    }

    public void setReady(Runnable value){
        ready(value);
    }
    public void ready(Runnable value) {
        if(body!=null&&body.getMediaPlayer()!=null){
            if(Platform.isFxApplicationThread()) body.getMediaPlayer().setOnReady(value);
            else Platform.runLater(() -> body.getMediaPlayer().setOnReady(value));
        }
    }

    public void setRepeat(Runnable value){
        repeat(value);
    }
    public void repeat(Runnable value) {
        if(body!=null&&body.getMediaPlayer()!=null){
            if(Platform.isFxApplicationThread()) body.getMediaPlayer().setOnRepeat(value);
            else Platform.runLater(() -> body.getMediaPlayer().setOnRepeat(value));
        }
    }

    public void setStalled(Runnable value){
        stalled(value);
    }
    public void stalled(Runnable value) {
        if(body!=null&&body.getMediaPlayer()!=null){
            if(Platform.isFxApplicationThread()) body.getMediaPlayer().setOnStalled(value);
            else Platform.runLater(() -> body.getMediaPlayer().setOnStalled(value));
        }
    }

    public void setSeek(double percentage){
        seek(percentage);
    }
    public void seek(double percentage) {
        if(body!=null&&body.getMediaPlayer()!=null){
            Duration duration = body.getMediaPlayer().getMedia().getDuration().multiply(percentage);
            if(Platform.isFxApplicationThread()) body.getMediaPlayer().seek(duration);
            else Platform.runLater(() -> body.getMediaPlayer().seek(duration));
        }
    }

    public void setVolume(double percentage){
        volume(percentage);
    }
    public double getVolume(){
        return body.getMediaPlayer().getVolume();
    }
    public void volume(double volume){
        if(body!=null&&body.getMediaPlayer()!=null){
            if(Platform.isFxApplicationThread()) body.getMediaPlayer().setVolume(volume);
            else Platform.runLater(() -> body.getMediaPlayer().setVolume(volume));
        }
    }

    public void mute(){
        if(body!=null&&body.getMediaPlayer()!=null){
            boolean isMute = body.getMediaPlayer().isMute();
            if(Platform.isFxApplicationThread()) body.getMediaPlayer().setMute(!isMute);
            else Platform.runLater(() -> body.getMediaPlayer().setMute(!isMute));
        }
    }

    public void setMute(boolean mute){
        mute(mute);
    }
    public boolean isMute(){
        return body.getMediaPlayer().isMute();
    }
    public void mute(boolean mute){
        if(body!=null&&body.getMediaPlayer()!=null){
            if(Platform.isFxApplicationThread()) body.getMediaPlayer().setMute(mute);
            else Platform.runLater(() -> body.getMediaPlayer().setMute(mute));
        }
    }

    public void setCycle(int cycle){
        cycle(cycle);
    }
    public int getCycle(){
        return body.getMediaPlayer().getCycleCount();
    }
    public void cycle(int cycle){
        if(body!=null&&body.getMediaPlayer()!=null){
            if(Platform.isFxApplicationThread()) body.getMediaPlayer().setCycleCount(cycle);
            else Platform.runLater(() -> body.getMediaPlayer().setCycleCount(cycle));
        }
    }
}
