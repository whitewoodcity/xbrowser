package com.whitewoodcity.controller;

import com.whitewoodcity.core.node.AnimationTimer;
import com.whitewoodcity.core.node.input.KeyEventHandler;
import com.whitewoodcity.core.node.input.MouseEventHandler;
import com.whitewoodcity.util.Res;
import javafx.beans.property.StringProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import java.util.*;

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

    public static void main(String[] args) throws Exception{
        App app = new App();
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
