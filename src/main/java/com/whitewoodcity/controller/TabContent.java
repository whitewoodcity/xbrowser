package com.whitewoodcity.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.whitewoodcity.Main;
import com.whitewoodcity.core.bean.CSS;
import com.whitewoodcity.core.bean.Class;
import com.whitewoodcity.core.bean.Script;
import com.whitewoodcity.core.bean.XmlV;
import com.whitewoodcity.core.node.input.KeyEventHandler;
import com.whitewoodcity.core.node.input.MouseEventHandler;
import com.whitewoodcity.core.parse.LayoutInflater;
import com.whitewoodcity.ui.ExceptionBox;
import com.whitewoodcity.util.Res;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import org.xmlpull.v1.XmlPullParserException;

import javax.script.ScriptEngine;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

public class TabContent extends App implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    private HBox header;

    @FXML
    private TextField urlInput;

    @FXML
    private Button fileSelector;

    @FXML
    private Button urlLocator;

    @FXML
    private Button fileSaver;

    @FXML
    private Button exceptionButton;

    @FXML
    private StackPane container;
    private Rectangle containerClip = new Rectangle();

    private Tab tab;

    private File directory;
    private Map<String, Object> preload = new HashMap<>();
    private Map<String, com.whitewoodcity.core.node.Node> context = new HashMap<>();
    private WebClient client;
    private ScriptEngine scriptEngine;
    private Node parent;
    private WebView webView;
    private Task loadingTask;
    private ExceptionBox exceptionBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = WebClient.create(Main.vertx);
        header.setSpacing(10);
        header.setPadding(new Insets(10));
        urlInput.prefWidthProperty().bind(header.widthProperty().subtract(60)
                .subtract(fileSelector.widthProperty()).subtract(urlLocator.widthProperty())
                .subtract(fileSaver.widthProperty()).subtract(exceptionButton.widthProperty()));
        container.layoutYProperty().bind(header.heightProperty());

        containerClip.widthProperty().bind(container.widthProperty());
        containerClip.heightProperty().bind(container.heightProperty());
        container.setClip(containerClip);

        container.setOnDragOver(event -> event.acceptTransferModes(TransferMode.ANY));

        try {
            directory = Res.getTempDirectory(UUID.randomUUID() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        exceptionBox = new ExceptionBox(exceptionButton);
        exceptionBox.hide();

        processParent(ParentType.TITLE, null,null);
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    @FXML
    public void loadEntry(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            loadUrl(keyEvent);
        }
    }

    @FXML
    private void loadUrl(Event event) {
        String url = urlInput.getText();
        if (url == null){
            //do nothing
        }else if (url.startsWith("file:")) {
            try {
                URI uri = new URI(url);
                File file = new File(uri);
                loadFile(file);
            } catch (Exception e) {
                handleExceptionMessage(e);
            }
        } else {
            loadWeb(url);
        }
    }

    public void loadWeb(String url) {
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        String immutableUrl = url;
        try {
            client.getAbs(url).send(ar -> {
                if (ar.succeeded()) {
                    handleHttpResponse(immutableUrl, ar.result());
                } else {
                    Throwable throwable = ar.cause();
                    handleExceptionMessage(throwable);
                }
            });
        } catch (Exception e) {
            handleExceptionMessage(e);
        }
    }

    private void handleHttpResponse(String url, HttpResponse response) {
        ParentType type;
        if (url.endsWith("xmlv") ||
                (response.getHeader("Content-Type") != null &&
                        response.getHeader("Content-Type").endsWith("xmlv"))) {
            type = ParentType.GROUP;
        } else {
            type = ParentType.WEB_VIEW;
        }
        String result = response.bodyAsString();
        Platform.runLater(() -> processParent(type, result, url));
    }

    private void handleExceptionMessage(Throwable e, String message) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        Platform.runLater(() -> processParent(ParentType.ERROR_MESSAGE, sw.toString(), message));
    }

    private void handleExceptionMessage(Throwable e) {
        handleExceptionMessage(e, e.getMessage());
    }

    private void processParent(ParentType type, String result, String urlOrMsg) {
        removeParent();
        switch (type) {
            case TITLE:
                Image image = new Image("logo/logo.png");
                ImageView imageView = new ImageView(image);
                imageView.setPreserveRatio(true);
                double height = Screen.getPrimary().getBounds().getHeight()/5;
                if(height*image.getWidth()/image.getHeight()>Screen.getPrimary().getBounds().getWidth()/3){
                    imageView.setFitWidth(Screen.getPrimary().getBounds().getWidth()/3);
                }else{
                    imageView.setFitHeight(height);
                }
                parent = imageView;
                break;
            case GROUP:
                container.setPadding(new Insets(0));
                try {

                    XmlV xmlV = new XmlMapper().readValue(result, XmlV.class);

                    tab.setText(xmlV.getTitle());

                    if (xmlV.getCsses() != null && xmlV.getCsses().length > 0) {
                        for (CSS css : xmlV.getCsses()) {
                            processCss(css);
                        }
                    }

                    Map<String, String> resources = super.parsePreloadString(xmlV.generateResources());

                    Group group = new Group();
                    ProgressBar progressBar = new ProgressBar();
                    Label label = new Label("0");
                    progressBar.prefWidthProperty().bind(container.widthProperty().multiply(0.8));
                    label.layoutYProperty().bind(progressBar.layoutYProperty().add(progressBar.heightProperty()));
                    label.layoutXProperty().bind(progressBar.layoutXProperty().add(progressBar.widthProperty()).subtract(label.widthProperty()));
                    group.getChildren().addAll(progressBar, label);
                    parent = group;

                    loadingTask = new Task() {

                        @Override
                        protected Object call() throws Exception {
                            Platform.runLater(() -> progressBar.setProgress(0));

                            for (int i = 0; i < downloadList.size(); i++) {
                                String url = downloadList.get(i);
                                Res.downLoadFromUrl(url, Res.getDefaultDirectory(),
                                        url.replaceFirst("^(http(s?)://www\\.|http(s?)://|www\\.)", ""), label.textProperty());
                                double progress = i + 1;
                                Platform.runLater(() -> progressBar.setProgress(progress / downloadList.size() * 0.5));
                            }

                            Platform.runLater(() -> progressBar.setProgress(0.5));

                            for (String key : resources.keySet()) {
                                String value = resources.get(key);
                                String filename = value.replaceFirst("^(http(s?)://www\\.|http(s?)://|www\\.)", "");
                                String uri = Paths.get(Res.getDefaultDirectory() + File.separator + filename).toUri().toString();
                                if (resources.get(key).endsWith("mp3") || resources.get(key).endsWith("mp4")) {
                                    preload.put(key, new Media(uri));
                                } else if (resources.get(key).endsWith("wav")) {
                                    preload.put(key, new AudioClip(uri));
                                } else {
                                    preload.put(key, new Image(uri));
                                }
                                Platform.runLater(() -> progressBar.setProgress(((double) preload.size()) / resources.size() * 0.5 + 0.5));
                            }

                            return null;
                        }
                    };

                    loadingTask.setOnSucceeded(value -> {

                        try {
                            context.clear();

                            parent = xmlV.generateNode(this).getNode();

                            if (xmlV.getClasses() != null && xmlV.getClasses().length > 0) {
                                for (Class clazz : xmlV.getClasses()) {
                                    processClass(clazz);
                                }
                            }

                            if (xmlV.getScripts() != null && xmlV.getScripts().length > 0) {
                                for (Script script : xmlV.getScripts()) {
                                    processScript(script);
                                }
                            }

                            container.getChildren().clear();
                            container.getChildren().add(0, parent);
                            container.requestFocus();
                        } catch (Exception e) {
                            handleExceptionMessage(e);
                        }
                    });

                    Thread thread = new Thread(loadingTask);
                    thread.setDaemon(true);
                    thread.start();
                } catch (Throwable e) {
                    handleExceptionMessage(e, result);
                    return;
                }

                break;
            case ERROR_MESSAGE:
                TextArea errorMsg = new TextArea();
                errorMsg.setPrefHeight(container.getHeight() - 20);
                errorMsg.setText(result + "\n" + urlOrMsg);
                container.setPadding(new Insets(10));
                parent = errorMsg;
                tab.textProperty().unbind();
                tab.setText(urlOrMsg);

                break;
            default:
                container.setPadding(new Insets(0));
                if (webView == null) {
                    webView = new WebView();
                }
                webView.getEngine().loadContent(result);
                tab.textProperty().unbind();
                tab.textProperty().bind(webView.getEngine().titleProperty());
                webView.getEngine().load(urlOrMsg);

                parent = webView;

                break;
        }

        urlInput.setText(urlOrMsg);
        container.getChildren().add(0, parent);
        container.requestFocus();
    }

    private void removeParent() {
        super.dispose();
        preload.clear();
        if (loadingTask != null) loadingTask.cancel();
        scriptEngine = null;
        container.getChildren().clear();
        exceptionBox.clearExceptionMessage();
        container.getStylesheets().clear();
    }

    public HBox getHeader() {
        return header;
    }

    public StackPane getContainer() {
        return container;
    }

    private void loadFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            reader.lines().forEach(line -> sb.append(line).append("\n"));
            ParentType type = ParentType.ERROR_MESSAGE;
            if (file.getName().endsWith(".xmlv")) {
                type = ParentType.GROUP;
            }
            processParent(type, sb.toString(), "");
            urlInput.setText(file.toURI().toString());
        } catch (Exception e) {
            handleExceptionMessage(e);
        }
    }

    @FXML
    public void onFileDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasFiles()) {
            File file = dragboard.getFiles().get(0);
            loadFile(file);
        }
    }

    @FXML
    public void onFileSelector(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        FileSystemView fsv = FileSystemView.getFileSystemView();
        chooser.setInitialDirectory(fsv.getHomeDirectory());
        File file = chooser.showOpenDialog(container.getScene().getWindow());
        if (file == null) {
            return;
        }
        loadFile(file);
    }

    @FXML
    private void displayExceptionMessage(ActionEvent event){
        if(exceptionBox.isShowing()) exceptionBox.hide();
        else exceptionBox.show();
    }

    @FXML
    public void saveFile(ActionEvent event) {
        String url = urlInput.getText();
        try {
            if (url == null||!url.startsWith("file:")) return;
            URI uri = new URI(url);
            File file = new File(uri);
            if (!(parent instanceof TextArea)) return;
            String content = ((TextArea) parent).getText();
            Res.saveFile(file, content);
        } catch (Exception e) {
            handleExceptionMessage(e);
        }
    }

    public Map<String, com.whitewoodcity.core.node.Node> getContext() {
        return context;
    }

    public void send(JsonObject json, String method, String action) {

        HttpMethod m = HttpMethod.POST;
        if (method != null) {
            switch (method.trim().toLowerCase()) {
                case "get":
                    m = HttpMethod.GET;
                    break;
                case "delete":
                    m = HttpMethod.DELETE;
                    break;
                case "put":
                    m = HttpMethod.PUT;
                    break;
                default:
                    break;
            }
        }
        if (action == null || action.isEmpty()) return;

        String url = action.trim();
        if (!url.startsWith("http")) {
            action = "http://" + action;
        }

        if (json.isEmpty()) {
            client.requestAbs(m, action)
                    .send(ar -> {
                        if (ar.succeeded()) handleHttpResponse(url, ar.result());
                        else handleExceptionMessage(ar.cause());
                    });
        } else {
            client.requestAbs(m, action)
                    .sendJsonObject(json, ar -> {
                        if (ar.succeeded()) handleHttpResponse(url, ar.result());
                        else handleExceptionMessage(ar.cause());
                    });
        }
    }

    public void submit(MultiMap form, String method, String action) {

        HttpMethod m = HttpMethod.POST;
        if (method != null) {
            switch (method.trim().toLowerCase()) {
                case "get":
                    m = HttpMethod.GET;
                    break;
                case "delete":
                    m = HttpMethod.DELETE;
                    break;
                case "put":
                    m = HttpMethod.PUT;
                    break;
                default:
                    break;
            }
        }
        if (action == null || action.isEmpty()) return;

        String url = action.trim();
        if (!url.startsWith("http")) {
            action = "http://" + action;
        }

        if (form.isEmpty()) {
            client.requestAbs(m, action)
                    .send(ar -> {
                        if (ar.succeeded()) handleHttpResponse(url, ar.result());
                        else handleExceptionMessage(ar.cause());
                    });
        } else {
            client.requestAbs(m, action)
                    .sendForm(form, ar -> {
                        if (ar.succeeded()) handleHttpResponse(url, ar.result());
                        else handleExceptionMessage(ar.cause());
                    });
        }
    }

    public void close() {
        removeParent();
        if (client != null) client.close();
        try {
            Res.removeTempDirectory(directory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> getPreload() {
        return preload;
    }

    @Override
    public MouseEventHandler getMouse() {

        if (mouseEventHandler == null) {
            mouseEventHandler = new MouseEventHandler();
            container.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEventHandler);
            container.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseEventHandler);
            container.addEventHandler(MouseEvent.MOUSE_MOVED, mouseEventHandler);
        }

        return mouseEventHandler;
    }

    @Override
    public KeyEventHandler getKey() {

        if (keyEventHandler == null) {
            keyEventHandler = new KeyEventHandler();
            container.addEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);
            container.addEventHandler(KeyEvent.KEY_RELEASED, keyEventHandler);
            container.setFocusTraversable(true);
        }

        return keyEventHandler;
    }

    @Override
    protected void disposeMouse() {
        if (mouseEventHandler != null) {
            container.removeEventHandler(MouseEvent.MOUSE_PRESSED, mouseEventHandler);
            container.removeEventHandler(MouseEvent.MOUSE_RELEASED, mouseEventHandler);
            container.removeEventHandler(MouseEvent.MOUSE_MOVED, mouseEventHandler);
            mouseEventHandler = null;
        }
    }

    @Override
    protected void disposeKey() {
        if (keyEventHandler != null) {
            container.removeEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);
            container.removeEventHandler(KeyEvent.KEY_RELEASED, keyEventHandler);
            keyEventHandler = null;
        }
    }

    private void processCss(CSS css) {
        try {
            if(css.getHref()!=null&&!css.getHref().trim().equals("")) {
                client.getAbs(css.getHref()).send(ar -> {
                    if (ar.succeeded()) {
                        String result = ar.result().bodyAsString();
                        Platform.runLater(() -> {
                            try {
                                processCss(result);
                            } catch (Throwable throwable) {
                                handleThrowableMessage(throwable);
                            }
                        });
                    } else {
                        handleThrowableMessage(ar.cause());
                    }
                });
            }
        }catch (Throwable throwable){
            handleThrowableMessage(throwable);
        }

        try {
            processCss(css.getCss());
        } catch (Throwable throwable) {
            handleThrowableMessage(throwable);
        }
    }

    private void processCss(String css) throws Exception {
        File cssFile = Res.getTempFile(directory, "css");
        BufferedWriter fos = new BufferedWriter(new FileWriter(cssFile));
        fos.write(css);
        fos.flush();
        fos.close();
        container.getStylesheets().add(cssFile.toURI().toString());
    }

    private void processClass(Class clazz) throws Exception {
        Main.vertx.executeBlocking(future -> {
            try {
                URL url = new URL(clazz.getUrl());
                URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
                java.lang.Class<?> targetClass = urlClassLoader.loadClass(clazz.getName());
                Object object = targetClass.getDeclaredConstructor().newInstance();

                object.getClass().getDeclaredMethod("setApp", Object.class).invoke(object, this);
                object.getClass().getDeclaredMethod("setContext", Map.class).invoke(object, context);
                object.getClass().getDeclaredMethod("setPreload", Map.class).invoke(object, preload);

                future.complete(object);
            } catch (Throwable e) {
                future.fail(e);
            }
        }, res -> {
            if (res.succeeded()) {
                Platform.runLater(() -> {
                    try {
                        res.result().getClass().getDeclaredMethod(clazz.getFunction(), null).invoke(res.result());
                    } catch (Throwable throwable) {
                        handleThrowableMessage(throwable);
                    }
                });
            } else
                handleThrowableMessage(res.cause());
        });
    }

    private void processScript(Script script) {
        String scriptType = script.getType();
        scriptEngine = Main.scriptEngineManager.getEngineByName(scriptType);

        scriptEngine.put("app", this);
        scriptEngine.put("preload", preload);
        scriptEngine.put("context", context);

        for (String id : preload.keySet()) {
            scriptEngine.put(id, preload.get(id));
        }
        for (String id : context.keySet()) {
            scriptEngine.put(id, context.get(id));
        }

        try {
            if(script.getHref()!=null&&!script.getHref().trim().equals("")) {
                client.getAbs(script.getHref()).send(ar -> {
                    if (ar.succeeded()) {
                        String result = ar.result().bodyAsString();
                        Platform.runLater(() -> {
                            try {
                                scriptEngine.eval(result);
                            } catch (Throwable throwable) {
                                handleThrowableMessage(throwable);
                            }
                        });
                    } else {
                        handleThrowableMessage(ar.cause());
                    }
                });
            }
        }catch (Throwable throwable){
            handleThrowableMessage(throwable);
        }

        try {
            scriptEngine.eval(script.getScript());
        } catch (Throwable throwable) {
            handleThrowableMessage(throwable);
        }
    }

    private void handleThrowableMessage(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        Platform.runLater(()->exceptionBox.setExceptionMessage(sw.toString()));
    }
}
