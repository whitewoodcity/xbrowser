package com.whitewoodcity.controller;

import com.whitewoodcity.Main;
import com.whitewoodcity.core.node.AnimationTimer;
import com.whitewoodcity.core.node.input.KeyEventHandler;
import com.whitewoodcity.core.node.input.MouseEventHandler;
import com.whitewoodcity.ui.ExceptionBox;
import com.whitewoodcity.util.Res;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.json.JsonObject;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.nio.file.Paths;
import java.util.*;

public abstract class App {
    protected MediaPlayer mediaPlayer;
    protected AnimationTimer timer;
    protected MouseEventHandler mouseEventHandler;
    protected KeyEventHandler keyEventHandler;

    private Buffer buffer;
    private DatagramSocket datagramSocket;

    private ExceptionBox exceptionBox;

    public void initialize(Button exceptionButton){
        exceptionBox = new ExceptionBox(exceptionButton);
        exceptionBox.hide();
    }

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
        if (mouseEventHandler!=null) disposeMouse();
        if (keyEventHandler!=null) disposeKey();
        exceptionBox.clearExceptionMessage();
        if (datagramSocket !=null){
            datagramSocket.close(ar -> datagramSocket = null);
        }
        if (buffer!=null) buffer = null;
    }

    public void focus(com.whitewoodcity.core.node.Node node){
        node.getNode().requestFocus();
    }

    protected List<String> downloadList = new ArrayList<>();

    public Map<String, String> parsePreloadString(List<String> strings) throws Exception{
        Map<String, String> preload = new HashMap<>();
        downloadList.clear();

        for(String string:strings){
            if(string.matches("^((n|N)ew:|(r|R)enew:).*=.*$")){
                String key = string.replaceFirst("^((n|N)ew:|(r|R)enew:)","");
                String value = parseKeyValuePair(key, preload);
                downloadList.add(value);
            }else if(string.matches("^.*=.*$")){
                String value = parseKeyValuePair(string, preload);
                File dir = Res.getDefaultDirectory();
                File file = new File(dir.getAbsolutePath()+File.separator+value.replaceFirst("^(http(s?)://www\\.|http(s?)://|www\\.)",""));
                if(!file.exists()) downloadList.add(value);
            }
        }

        return preload;
    }

    public String parseKeyValuePair(String string, Map<String, String> map){
        String[] kv = string.split("=");
        map.put(kv[0],kv[1]);
        return kv[1];
    }

    public abstract MouseEventHandler getMouse();

    public abstract KeyEventHandler getKey();

    protected abstract void disposeMouse();

    protected abstract void disposeKey();

    public abstract void submit(MultiMap form, String method, String action);

    public abstract void send(JsonObject json, String method, String action);

    public void send(int port, String address, String value){
        if(datagramSocket==null) datagramSocket = Main.vertx.createDatagramSocket();
        datagramSocket.send(value, port, address, ar->{});
    }

    public void listen(int port){
        listen(port, false);
    }

    public void listen(int port, int length){
        if(buffer==null) buffer = Buffer.buffer();
        if(datagramSocket==null) datagramSocket = Main.vertx.createDatagramSocket();
        datagramSocket.listen(port,"0.0.0.0",asyncResult ->{
            if (asyncResult.succeeded()) {
                datagramSocket.handler(packet -> {
                    // Do something with the packet
                    Platform.runLater(() ->{
                        if(buffer!=null){
                            buffer.appendBuffer(packet.data());
                            if(buffer.length()>length*4){
                                buffer = buffer.getBuffer(buffer.length()-length*4,buffer.length());
                            }
                        }
                    });
                });
            } else {
                handleThrowableMessage(asyncResult.cause());
            }
        });
    }

    public void listen(int port, boolean accumulated){
        if(buffer==null) buffer = Buffer.buffer();
        if(datagramSocket==null) datagramSocket = Main.vertx.createDatagramSocket();
        datagramSocket.listen(port,"0.0.0.0",asyncResult ->{
            if (asyncResult.succeeded()) {
                datagramSocket.handler(packet -> {
                    // Do something with the packet
                    Platform.runLater(() ->{
                        if(accumulated&&buffer!=null){
                            buffer.appendBuffer(packet.data());
                        }
                        else buffer = packet.data();
                    });
                });
            } else {
                handleThrowableMessage(asyncResult.cause());
            }
        });
    }

    public Buffer getBuffer(){
        return buffer;
    }

    public String received(){
        if(buffer==null) return "";
        return buffer.toString();
    }

    public void flush(){
        buffer = Buffer.buffer();
    }

    protected void handleThrowableMessage(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        Platform.runLater(()->exceptionBox.setExceptionMessage(sw.toString()));
    }

    protected void displayOrHideExceptionBox(){
        if(exceptionBox.isShowing()) exceptionBox.hide();
        else exceptionBox.show();
    }

    public static void main(String[] args) throws Exception{
        App app = new TabContent();
        List<String> list = new ArrayList<>();
        Set<String> download = new HashSet<>();
        list.add("new:test.wav=http://fdsjaflkdjsakljlfds.wav");
        list.add("New:hello.png=http://abccfds.com/test.png");
        list.add("renew:hello0.png=http://abccfds.com/test.png");
        list.add("Renew:hello1.png=http://abccfds.com/test.png");
        list.add("hello2.png=http://abccfds.com/test.png");

        System.out.println(app.parsePreloadString(list));
        System.out.println(download);

        String s = "www.abccfds.com/test.png".replaceFirst("^(http(s?)://www\\.|http(s?)://|www\\.)","");//

        System.out.println(s);
    }
}
