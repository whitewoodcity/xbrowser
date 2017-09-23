package com.whitewoodcity.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.whitewoodcity.Main;
import com.whitewoodcity.core.bean.CSS;
import com.whitewoodcity.core.bean.VXml;
import com.whitewoodcity.core.bean.XmlV;
import com.whitewoodcity.core.node.Button;
import com.whitewoodcity.core.parse.PageParser;
import com.whitewoodcity.util.Res;
import io.vertx.ext.web.client.WebClient;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TabContent implements Initializable {

    @FXML
    private Group group;

    @FXML
    private HBox header;

    @FXML
    private TextField urlInput;

    @FXML
    private StackPane imgIcn;

    private Tab tab;

    private WebClient client;

    private Parent parent;
    private WebView webView;
    private PageParser pageParser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = WebClient.create(Main.vertx);
        header.setSpacing(0);
        header.setPadding(new Insets(10));
        urlInput.prefWidthProperty().bind(header.widthProperty().subtract(20).subtract(imgIcn.widthProperty()));
        pageParser=new PageParser();
//        container.prefWidthProperty().bind(tab.getTabPane().widthProperty());
//        container.prefHeightProperty().bind(vBox.heightProperty().subtract(header.getHeight()));
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
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        loadWeb(url);

    }

    private void loadFxml(String url) {
        client.getAbs(url)
                .send(ar ->{
                    if(ar.succeeded()){
                        //System.out.println(ar.result().getHeader("Content-Type"));
                        //System.out.println(ar.result().bodyAsString());
                        String content=ar.result().bodyAsString();
                        StringReader reader=new StringReader(content);
                        VXml vXml=pageParser.paresReader(reader, VXml.class);

                        //渲染第一步，载入fxml
                        List<CSS> css=vXml.getCsses();
                        BufferedWriter fos = null;
                        InputStream is=null;
                        try {
                            File cssFile=Res.getTempFile();
                            fos=new BufferedWriter(new FileWriter(cssFile));
                            fos.write(css.get(0).getCss());
                            fos.flush();
                            fos.close();
                            String f=content.substring(content.indexOf("<fxml>")+6,content.lastIndexOf("</fxml>"));
                            is=new ByteArrayInputStream(f.getBytes());
                            FXMLLoader loader=new FXMLLoader();
                            Parent parent=loader.load(is);
                            parent.getStylesheets().add(cssFile.toURI().toURL().toExternalForm());
                            Platform.runLater(()->{
                                processParent(ParentType.FXML,null,null);
                                addNode(parent);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            try {
                                if(fos!=null){
                                    fos.close();
                                }
                                if (is != null) {
                                    is.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }else{
                        Platform.runLater(() -> {
                            handleExceptionMessage(ar.cause());
                        });
                    }
                });
    }

    private void loadWeb(String url) {
        try {
            client.getAbs(url).send(ar -> {
                if (ar.succeeded()) {
                    ParentType type;
                    if (url.endsWith("xmlv") || ar.result().getHeader("Content-Type").endsWith("xmlv")) {
                        type = ParentType.GROUP;
                    } else {
                        type = ParentType.WEB_VIEW;
                    }
                    String result = ar.result().bodyAsString();
                    Platform.runLater(() -> {
                        processParent(type, result, url);
                    });
                } else {
                    Throwable throwable = ar.cause();
                    Platform.runLater(() -> {
                        handleExceptionMessage(throwable);
                    });
                }
            });
        } catch (Exception e) {
            handleExceptionMessage(e);
        }
    }

    private void handleExceptionMessage(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        processParent(ParentType.ERROR_MESSAGE, sw.toString(), null);
    }

    XmlMapper xmlMapper = new XmlMapper();

    private void processParent(ParentType type, String result, String url){
        removeParent();
        StackPane headerArea = (StackPane) tab.getTabPane().lookup(".tab-header-area");
        switch (type) {
            case GROUP:
                StackPane stackPane = new StackPane();
                com.whitewoodcity.core.node.Group group = new com.whitewoodcity.core.node.Group();
                try {
                    XmlV xmlV = xmlMapper.readValue(result, XmlV.class);
                    Button button = new Button("test");
                    button.setWidth(100);
                    button.setX(100-button.getWidth()/2);
                    System.out.println(button.getWidth());
                    System.out.println(button.getX());
                    button.setY(500);
                    group.add(button);
                    parent = stackPane;
                    stackPane.layoutYProperty().bind(header.heightProperty());
                    stackPane.getChildren().add(group.getNode());
                    this.group.getChildren().add(stackPane);
                } catch (Exception e) {
                    handleExceptionMessage(e);
                }
                break;
            case ERROR_MESSAGE:
                TextArea errorMsg = new TextArea();
                errorMsg.setText(result);
                errorMsg.setEditable(false);
                errorMsg.setFocusTraversable(false);
                errorMsg.setLayoutY(header.getHeight());
                errorMsg.setLayoutX(3);
                errorMsg.prefWidthProperty().bind(tab.getTabPane().widthProperty().subtract(6));
                errorMsg.prefHeightProperty().bind(tab.getTabPane().heightProperty()
                        .subtract(headerArea.heightProperty())
                        .subtract(header.getHeight()).subtract(3));
                this.group.getChildren().add(errorMsg);
                parent = errorMsg;
                break;
            default:
                if(webView==null){
                    webView = new WebView();
                    webView.setLayoutY(header.getHeight());
                    webView.prefWidthProperty().bind(tab.getTabPane().widthProperty());
                    webView.prefHeightProperty().bind(tab.getTabPane().heightProperty()
                            .subtract(headerArea.heightProperty())
                            .subtract(header.getHeight()));
                }
                webView.getEngine().loadContent(result);
                tab.textProperty().unbind();
                tab.textProperty().bind(webView.getEngine().titleProperty());
                webView.getEngine().load(url);

                this.group.getChildren().add(webView);
                parent = webView;
                break;
        }
    }

    public void removeParent() {
        group.getChildren().remove(parent);
    }

    public HBox getHeader() {
        return header;
    }

    public void addNode(Parent node){
        parent=node;
        node.setLayoutY(header.getLayoutY()+header.getHeight());
        group.getChildren().add(node);
    }
}
