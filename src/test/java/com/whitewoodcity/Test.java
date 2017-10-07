package com.whitewoodcity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.whitewoodcity.core.bean.Script;
import com.whitewoodcity.core.bean.VXml;
import com.whitewoodcity.core.bean.XmlV;
import com.whitewoodcity.core.parse.LayoutInflater;
import com.whitewoodcity.core.parse.PageParser;
import javafx.scene.layout.StackPane;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static String vxml="<vxml>\n" +
            "\n" +
            "    <fxml>\n" +
            "        <?import java.lang.*?>\n" +
            "        <?import java.util.*?>\n" +
            "        <?import javafx.scene.*?>\n" +
            "        <?import javafx.scene.control.*?>\n" +
            "        <?import javafx.scene.layout.*?>\n" +
            "\n" +
            "        <BorderPane xmlns=\"http://javafx.com/javafx\"\n" +
            "                    xmlns:fx=\"http://javafx.com/fxml\"\n" +
            "                    prefHeight=\"400.0\" prefWidth=\"600.0\">\n" +
            "\n" +
            "            <center>\n" +
            "                <Button text=\"Hello Button\" fx:id=\"testBtn\"/>\n" +
            "            </center>\n" +
            "\n" +
            "        </BorderPane>\n" +
            "    </fxml>\n" +
            "\n" +
            "    <script type=\"js\" >\n" +
            "\n" +
            "\n" +
            "    </script>\n" +
            "    <css>\n" +
            "        #testBtn{\n" +
            "            -fx-background-color:red;\n" +
            "        }\n" +
            "    </css>\n" +
            "</vxml>";

    public static final String layout="<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
            "<Button/>";

    public static void main(String[] args) throws IOException, XmlPullParserException {
//        PageParser pageParser=new PageParser();
//        VXml script=pageParser.paresReader(new StringReader(vxml),VXml.class);
//        System.out.println(script.getfXml().getFxml());
//        System.out.println(script.getCsses().get(0).getCss());
//        ObjectMapper xmlMapper = new XmlMapper();
//        try {
//            XmlV xmlV = xmlMapper.readValue(vxml, XmlV.class);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        //System.out.println(vxml.substring(vxml.indexOf("<fxml>")+6,vxml.lastIndexOf("</fxml>")));
    }
}
