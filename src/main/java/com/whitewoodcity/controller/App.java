package com.whitewoodcity.controller;

import com.whitewoodcity.Main;
import com.whitewoodcity.core.node.AnimationTimer;
import com.whitewoodcity.core.node.input.KeyEventHandler;
import com.whitewoodcity.core.node.input.MouseEventHandler;
import com.whitewoodcity.ui.ExceptionBox;
import com.whitewoodcity.ui.Page;
import com.whitewoodcity.ui.PagePane;
import com.whitewoodcity.util.Res;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.json.JsonObject;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public abstract class App {
    protected PagePane pagePane;

    protected MediaPlayer mediaPlayer;
    protected AnimationTimer timer;
    protected MouseEventHandler mouseEventHandler;
    protected KeyEventHandler keyEventHandler;

    private Buffer buffer;
    private DatagramSocket datagramSocket;

    private ExceptionBox exceptionBox;

    public void initialize(Button exceptionButton) {
        exceptionBox = new ExceptionBox(exceptionButton);
        exceptionBox.hide();
    }

    public MediaPlayer play(Media media) {
        return play(media, Integer.MAX_VALUE);
    }

    public MediaPlayer play(Media media, int cycle) {
        return play(media, cycle, 1);
    }

    public MediaPlayer play(Media media, int cycle, double volume) {
        if (mediaPlayer != null) mediaPlayer.dispose();
        if (media == null) return null;
        if (cycle <= 0) return null;
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

    public AnimationTimer get_timer() {
        return timer();
    }

    public AnimationTimer gettimer() {
        return timer();
    }

    protected void dispose() {
        if (timer != null) timer.stop();
        if (mediaPlayer != null) mediaPlayer.dispose();
        if (mouseEventHandler != null) disposeMouse();
        if (keyEventHandler != null) disposeKey();
        exceptionBox.clearExceptionMessage();
        if (datagramSocket != null) {
            datagramSocket.close(ar -> {
                if (ar.succeeded())
                    datagramSocket = null;
                else {
                    handleThrowableMessage(ar.cause());
                }
            });
        }
        if (buffer != null) {
            buffer = null;
        }
    }

    public void focus(com.whitewoodcity.core.node.Node node) {
        node.getNode().requestFocus();
    }

    protected List<String> downloadList = new ArrayList<>();

    protected Map<String, String> parsePreloadString(List<String> strings) throws Exception {
        Map<String, String> preload = new HashMap<>();
        downloadList.clear();

        for (String string : strings) {
            if (string.matches("^((n|N)ew:|(r|R)enew:).*=.*$")) {
                String key = string.replaceFirst("^((n|N)ew:|(r|R)enew:)", "");
                String value = parseKeyValuePair(key, preload);
                downloadList.add(value);
            } else if (string.matches("^.*=.*$")) {
                String value = parseKeyValuePair(string, preload);
                File dir = Res.getDefaultDirectory();
                File file = new File(dir.getAbsolutePath() + File.separator + value.replaceFirst("^(http(s?)://www\\.|http(s?)://|www\\.)", ""));
                if (!file.exists()) downloadList.add(value);
            }
        }

        return preload;
    }

    private String parseKeyValuePair(String string, Map<String, String> map) {
        String[] kv = string.split("=");
        map.put(kv[0], kv[1]);
        return kv[1];
    }

    public abstract MouseEventHandler getMouse();

    public MouseEventHandler get_mouse(){
        return getMouse();
    }

    public MouseEventHandler getmouse(){
        return getMouse();
    }

    public MouseEventHandler mouse(){
        return getMouse();
    }

    public abstract KeyEventHandler getKey();

    public KeyEventHandler get_key(){
        return getKey();
    }

    public KeyEventHandler getkey(){
        return getKey();
    }

    public KeyEventHandler key(){
        return getKey();
    }

    protected abstract void disposeMouse();

    protected abstract void disposeKey();

    public void send(int port, String address, String value) {
        if (datagramSocket == null) datagramSocket = Main.vertx.createDatagramSocket();
        datagramSocket.send(value, port, address, ar -> {
            if (ar.failed()) {
                handleThrowableMessage(ar.cause());
            }
        });
    }

    public void listen(int port) {
        listen(port, false);
    }

    public void listen(int port, int length) {
        if (datagramSocket == null) datagramSocket = Main.vertx.createDatagramSocket();
        final int maxLen;
        if (length > 1000) maxLen = 1000;
        else maxLen = length;
        datagramSocket.listen(port, "0.0.0.0", asyncResult -> {
            if (asyncResult.succeeded()) {
                datagramSocket.handler(packet -> {
                    // Do something with the packet
                    Platform.runLater(() -> {
                        if (buffer != null) {
                            buffer.appendBuffer(packet.data());
                            if (buffer.length() > maxLen * 4) {
                                buffer = buffer.getBuffer(buffer.length() - maxLen * 4, buffer.length());
                            }
                        } else {
                            buffer = packet.data();
                        }
                    });
                });
            } else {
                handleThrowableMessage(asyncResult.cause());
            }
        });
    }

    private void listen(int port, boolean accumulated) {
        if (datagramSocket == null) datagramSocket = Main.vertx.createDatagramSocket();
        datagramSocket.listen(port, "0.0.0.0", asyncResult -> {
            if (asyncResult.succeeded()) {
                datagramSocket.handler(packet -> {
                    // Do something with the packet
                    Platform.runLater(() -> {
                        if (accumulated && buffer != null) {
                            buffer.appendBuffer(packet.data());
                        } else buffer = packet.data();
                    });
                });
            } else {
                handleThrowableMessage(asyncResult.cause());
            }
        });
    }

    public Buffer buffer() {
        return buffer;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public Buffer get_buffer() {
        return getBuffer();
    }

    public Buffer getbuffer() {
        return getBuffer();
    }

    public String received(String encoding){
        if (buffer == null) return "";
        return buffer.toString(encoding);
    }

    public String received() {
        if (buffer == null) return "";
        return buffer.toString();
    }

    public void flush() {
        buffer = Buffer.buffer();
    }

    protected void handleThrowableMessage(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        Platform.runLater(() -> exceptionBox.setExceptionMessage(sw.toString()));
    }

    protected void handleMessage(Object message) {
        String m;
        if (message != null){
            m = message.toString();
        }
        else m = null;
        Platform.runLater(() -> exceptionBox.setMessage(m));
    }

    protected void displayOrHideExceptionBox() {
        if (exceptionBox.isShowing()) exceptionBox.hide();
        else exceptionBox.show();
    }

    protected void decorateMenuButton(MenuButton menu) {
        menu.textProperty().bind(Main.namesMap.get("menu"));

        MenuItem newItem = new MenuItem();
        MenuItem loadItem = new MenuItem();
        MenuItem saveItem = new MenuItem();
        MenuItem refreshItem = new MenuItem();
        MenuItem closeItem = new MenuItem();
        Menu languageMenu = new Menu();

        MenuItem english = new MenuItem("English");
        MenuItem simChin = new MenuItem("简体中文");

        languageMenu.getItems().addAll(english, simChin);

        newItem.setOnAction(event -> {
            if (pagePane != null) {
                pagePane.buildPane();
            }
        });
        loadItem.setOnAction(event -> {
            Page page = (Page) pagePane.getSelectionModel().getSelectedItem();
            if (page != null) page.getController().selectFile(event);
        });
        saveItem.setOnAction(event -> {
            Page page = (Page) pagePane.getSelectionModel().getSelectedItem();
            if (page != null) page.getController().saveFile(event);
        });
        refreshItem.setOnAction(event -> {
            Page page = (Page) pagePane.getSelectionModel().getSelectedItem();
            if (page != null) page.getController().load();
        });
        closeItem.setOnAction(event -> {
            if (pagePane != null) {
                Tab page = pagePane.getSelectionModel().getSelectedItem();
                if (page != null) {
                    page.getOnClosed().handle(event);
                    pagePane.getTabs().remove(page);
                }
            }
        });
        english.setOnAction(value -> Main.generateNames(new Locale("en")));
        simChin.setOnAction(value -> Main.generateNames(new Locale("zh", "CN")));

        newItem.textProperty().bind(Main.namesMap.get("tab"));
        loadItem.textProperty().bind(Main.namesMap.get("load"));
        saveItem.textProperty().bind(Main.namesMap.get("save"));
        refreshItem.textProperty().bind(Main.namesMap.get("refresh"));
        closeItem.textProperty().bind(Main.namesMap.get("close"));
        languageMenu.textProperty().bind(Main.namesMap.get("language"));

        newItem.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN));
        loadItem.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN));
        saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
        refreshItem.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN));
        closeItem.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN));

        menu.getItems().addAll(newItem, loadItem, saveItem, refreshItem, closeItem, languageMenu);
    }

    public abstract void load(String url);

    public static void main(String[] args) throws Exception {
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

        String s = "www.abccfds.com/test.png".replaceFirst("^(http(s?)://www\\.|http(s?)://|www\\.)", "");//

        System.out.println(s);
    }
}
