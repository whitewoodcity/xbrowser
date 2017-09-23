package com.whitewoodcity;

import com.whitewoodcity.core.bean.CSS;
import com.whitewoodcity.core.bean.Script;
import com.whitewoodcity.core.bean.VXml;
import com.whitewoodcity.core.parse.PageParser;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.StringReader;
import java.util.List;

@RunWith(VertxUnitRunner.class)
public class MyTest {

    private static String script_str="<script type=\"ruby\"\n" +
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


    @Test
    public void test() {
        PageParser pageParser=new PageParser();
        //读文件
        File file=new File(ClassLoader.getSystemResource("test.vxml").getPath());
        VXml vXml=pageParser.parseFile(file,VXml.class);
        List<CSS> csses=vXml.getCsses();
        CSS css=csses.get(0);
        System.out.println(css);
        System.out.println(vXml.getScripts().get(0));
        Script script=pageParser.paresReader(new StringReader(script_str),Script.class);
        System.out.println(script);
    }
}
