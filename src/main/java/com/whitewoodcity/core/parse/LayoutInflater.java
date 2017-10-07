package com.whitewoodcity.core.parse;

import com.whitewoodcity.core.exception.InflateException;
import com.whitewoodcity.core.node.conrol.Button;
import com.whitewoodcity.core.view.View;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import javax.xml.stream.XMLOutputFactory;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class LayoutInflater {

    //private static final HashMap<String,Constructor<? extends View>> sConstructorMap=new HashMap<>();

    static final Class<?>[] mConstructorSignature = new Class[] {};
    final Object[] mConstructorArgs = new Object[0];

    XmlPullParser parser;

    private LayoutInflater(Reader reader) {
        try {
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            parser=factory.newPullParser();
            parser.setInput(reader);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public static LayoutInflater with(){
        return new LayoutInflater(new StringReader(layout));
    }

    public Node inflate(StackPane root) throws IOException, XmlPullParserException {

        Node result=root;
        int type;
        while ((type=parser.next())!=XmlPullParser.START_TAG&&
                type!=XmlPullParser.END_DOCUMENT);
        if(type!=XmlPullParser.START_TAG){
            throw new InflateException(parser.getPositionDescription()+":No Start tag found!");
        }
        String name=parser.getName();
        //创建父元素
        Node temp=createNodeFromTag(name);
        //创建父元素中的子元素
        inflateChildren(parser,temp);
        root.getChildren().add(temp);
        return result;
    }

    private Node createNodeFromTag(String tagName) {
        Class<? extends com.whitewoodcity.core.node.Node> clazz=null;
        String prefix="com.whitewoodcity.core.node.conrol.";
        try {
//            System.out.println(prefix+tagName);
            clazz=getClass().getClassLoader().loadClass(prefix+tagName).asSubclass(com.whitewoodcity.core.node.Node.class);
            com.whitewoodcity.core.node.Node view=clazz.newInstance();
            System.out.println(view);
            return view.getNode();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            throw  new InflateException("Error inflating class " + tagName,e);
        }
    }

     void inflateChildren(XmlPullParser parser, Node node) throws IOException, XmlPullParserException {
        final int depth=parser.getDepth();
        int type;
        while (((type = parser.next()) != XmlPullParser.END_TAG ||
                parser.getDepth() > depth) && type != XmlPullParser.END_DOCUMENT) {
            if(type!=XmlPullParser.START_TAG){
                continue;
            }
            String tagName=parser.getName();
            Node view=createNodeFromTag(tagName);
            Parent parent= (Parent) node;
            inflateChildren(parser,view);
            try {
                Method invoker=parent.getClass().getDeclaredMethod("getChildren");
                ObservableList<Node> childrens= (ObservableList<Node>) invoker.invoke(parent);
                childrens.add(view);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
     }

    public static final String layout="<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
            "<Button/>";


}
