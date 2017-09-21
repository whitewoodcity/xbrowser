package com.whitewoodcity;

import com.whitewoodcity.core.bean.CSS;
import com.whitewoodcity.core.bean.Script;
import com.whitewoodcity.core.bean.VXml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

public class Test {

    private static String vxml="<script type=\"ruby\"\n" +
            "            href=\"\"\n" +
            "            version=\"9.1.13.0\"\n" +
            "            link=\"http://central.maven.org/maven2/org/jruby/jruby-complete/9.1.13.0/jruby-complete-9.1.13.0.jar\">\n" +
            "        button001 = $button001\n" +
            "        button002 = $button002\n" +
            "        button002.layoutX.bind(button001.layoutX)\n" +
            "        button001.onAction = lambda{|value|\n" +
            "        print \"clicked\"\n" +
            "\n" +
            "    </script>";


    public static void main(String[] args) {
        try {
            JAXBContext jaxbContext=JAXBContext.newInstance(VXml.class);
            Unmarshaller us=jaxbContext.createUnmarshaller();
            //读文件
            /*File file=new File(ClassLoader.getSystemResource("test.xml").getPath());
            VXml vXml= (VXml) us.unmarshal(file);
            List<CSS> csses=vXml.getCsses();
            CSS css=csses.get(0);
            System.out.println(css);
            System.out.println(vXml.getScripts().get(0));*/
            //读流
            StringReader reader=new StringReader(vxml);
            Script script= (Script) us.unmarshal(reader);
            System.out.println(script);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
