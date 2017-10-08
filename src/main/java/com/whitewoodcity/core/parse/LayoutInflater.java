package com.whitewoodcity.core.parse;

import com.whitewoodcity.core.exception.InflateException;
import com.whitewoodcity.core.node.Node;
import com.whitewoodcity.core.node.layout.ViewGroup;
import javafx.scene.layout.StackPane;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class LayoutInflater {

    //private static final HashMap<String,Constructor<? extends View>> sConstructorMap=new HashMap<>();

    static final Class<?>[] mConstructorSignature = new Class[] {};
    final Object[] mConstructorArgs = new Object[0];
    private static final String[] sClassPrefixList = {
            "com.whitewoodcity.core.node.conrol.",
            "com.whitewoodcity.core.node.layout.",
            "com.whitewoodcity.core.node.canvas.",
            "com.whitewoodcity.core.node.chart."
    };

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
        int type;
        while ((type=parser.next())!=XmlPullParser.START_TAG&&
                type!=XmlPullParser.END_DOCUMENT);
        if(type!=XmlPullParser.START_TAG){
            throw new InflateException(parser.getPositionDescription()+":No Start tag found!");
        }
        String name=parser.getName();
        //创建父元素
        Node temp=createNodeFromTag(name);
        if(temp!=null){
            //创建父元素中的子元素
            inflateChildren(parser,temp);
            root.getChildren().add(temp.getNode());
        }
        return temp;
    }

    private Node createNodeFromTag(String tagName) {
        Node node = null;
        if(tagName.contains(".")){
            try {
                node=createNode(tagName,null);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            for (String prefix:sClassPrefixList){
                try {
                    node=createNode(tagName,prefix);
                    if(node!=null){
                        return node;
                    }
                } catch (ClassNotFoundException e) {

                }
            }
        }
        return node;
    }

    private Node createNode(String tagName,String prefix) throws ClassNotFoundException {
        Class<? extends Node> clazz=null;
        try {
            clazz=Node.class.getClassLoader().loadClass(prefix != null ? (prefix + tagName) : tagName).asSubclass(Node.class);
            Node view=clazz.newInstance();
            System.out.println(view);
            return view;
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw e;
        }
        return null;
    }

     private void inflateChildren(XmlPullParser parser, Node node) throws IOException, XmlPullParserException {
        final int depth=parser.getDepth();
        int type;
        while (((type = parser.next()) != XmlPullParser.END_TAG ||
                parser.getDepth() > depth) && type != XmlPullParser.END_DOCUMENT) {
            if(type!=XmlPullParser.START_TAG){
                continue;
            }
            String tagName=parser.getName();
            //创建当前标签Node
            Node view=createNodeFromTag(tagName);
            if(view!=null&&node instanceof ViewGroup){
                ViewGroup viewGroup= (ViewGroup) node;
                //查找当前标签其内是否还有其他标签如果没有就直接返回
                inflateChildren(parser,view);
                viewGroup.addNode(view);
            }
        }
     }

    public static final String layout="<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
            "<FrameLayout" +
            "    id=\"fl\"\n" +
            "    width=\"100\"\n" +
            "    height=\"100\"\n" +
            "    background=\"#FF0000\">\n" +
            "\n" +
            "    <Button/>\n" +
            "</FrameLayout>";


}
