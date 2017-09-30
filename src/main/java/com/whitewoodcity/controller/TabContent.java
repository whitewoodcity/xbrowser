package com.whitewoodcity.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.whitewoodcity.Main;
import com.whitewoodcity.core.bean.XmlV;
import com.whitewoodcity.core.node.AnimationTimer;
import com.whitewoodcity.core.node.input.MouseEventHandler;
import com.whitewoodcity.util.Res;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import javax.script.ScriptEngine;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class TabContent implements Initializable {

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
    private StackPane container;
    private Rectangle containerClip = new Rectangle();

    private Tab tab;

    private WebClient client;
    private ScriptEngine scriptEngine;
    private MouseEventHandler mouseEventHandler = new MouseEventHandler();
    private Node parent;
    private WebView webView;

    private AnimationTimer timer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = WebClient.create(Main.vertx);
        header.setSpacing(10);
        header.setPadding(new Insets(10));
        urlInput.prefWidthProperty().bind(header.widthProperty().subtract(50)
                .subtract(fileSelector.widthProperty()).subtract(urlLocator.widthProperty()).subtract(fileSaver.widthProperty()));
        container.layoutYProperty().bind(header.heightProperty());

        containerClip.widthProperty().bind(container.widthProperty());
        containerClip.heightProperty().bind(container.heightProperty());
        container.setClip(containerClip);

        container.setOnDragOver(event -> event.acceptTransferModes(TransferMode.ANY));

        container.addEventHandler(MouseEvent.MOUSE_PRESSED,mouseEventHandler);
        container.addEventHandler(MouseEvent.MOUSE_RELEASED,mouseEventHandler);
        container.addEventHandler(MouseEvent.MOUSE_MOVED,mouseEventHandler);
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
        if (url.startsWith("file:")) {
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

    XmlMapper xmlMapper = new XmlMapper();

    private void processParent(ParentType type, String result, String urlOrMsg) {
        removeParent();
        switch (type) {
            case GROUP:
                container.setPadding(new Insets(0));
                try {

                    XmlV xmlV = xmlMapper.readValue(result, XmlV.class);

                    System.out.println(xmlV.getPreload().getPreload());

                    if (xmlV.getScript() != null && xmlV.getScript().getScript()!=null &&
                            !xmlV.getScript().getScript().replace("\n","").trim().equals("")) {
                        String script = xmlV.getScript().getType();
                        script = script == null ? "javascript" : script;
                        scriptEngine = Main.scriptEngineManager.getEngineByName(script);
//                        scriptEngine= ScriptFactory.loadJRubyScript();
                    }

                    parent = xmlV.generateNode(this).getNode();

                    if (!xmlV.isCssEmpty()) {
                        File cssFile = Res.getTempFile("css");
                        BufferedWriter fos = new BufferedWriter(new FileWriter(cssFile));
                        fos.write(xmlV.getCss().getCss());
                        fos.flush();
                        fos.close();
                        container.getStylesheets().clear();
                        container.getStylesheets().add(cssFile.toURI().toString());

                        Platform.runLater(() -> {
                            parent.applyCss();
                            cssFile.delete();
                        });
                    }

                    if (scriptEngine != null) {
                        timer = new AnimationTimer();
                        scriptEngine.put("app", this);
                        scriptEngine.put("timer", timer);
                        scriptEngine.put("mouse", mouseEventHandler);

                        scriptEngine.eval(xmlV.getScript().getScript());
                    }

                } catch (Exception e) {
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
    }

    public void removeParent() {
        scriptEngine = null;
        if (timer != null) timer.stop();
        container.getChildren().clear();
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
    private void saveFile(ActionEvent event) {
        String url = urlInput.getText();
        try {
            if (!url.startsWith("file:")) return;
            URI uri = new URI(url);
            File file = new File(uri);
            if (!(parent instanceof TextArea)) return;
            String content = ((TextArea) parent).getText();
            Res.saveFile(file, content);
        } catch (Exception e) {
            handleExceptionMessage(e);
        }
    }

    public ScriptEngine getScriptEngine() {
        return scriptEngine;
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
        if (client != null) client.close();
        if (timer != null) timer.stop();
    }

}
