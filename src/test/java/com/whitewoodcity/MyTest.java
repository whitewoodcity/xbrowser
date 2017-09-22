package com.whitewoodcity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.whitewoodcity.core.bean.Script;
import com.whitewoodcity.core.bean.VXml;
import com.whitewoodcity.core.bean.XmlV;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.client.WebClient;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@RunWith(VertxUnitRunner.class)
public class MyTest {

    private static String vxml="<xmlv><script type=\"ruby\"\n" +
            "            href=\"\"\n" +
            "            version=\"9.1.13.0\"\n" +
            "            link=\"http://central.maven.org/maven2/org/jruby/jruby-complete/9.1.13.0/jruby-complete-9.1.13.0.jar\">\n" +
            "        button001 = $button001\n" +
            "        button002 = $button002\n" +
            "        button002.layoutX.bind(button001.layoutX)\n" +
            "        button001.onAction = lambda{|value|\n" +
            "        print \"clicked\"\n" +
            "\n}" +
            "    </script>    <jsonfx>\n" +
            "        {\n" +
            "        \"id\":\"group001\",\n" +
            "        \"type\":\"group\",\n" +
            "        \"children\":[{\n" +
            "        \"id\":\"button001\",\n" +
            "        \"type\":\"button\",\n" +
            "        \"layoutX\":\"100\"\n" +
            "        },{\n" +
            "        \"id\":\"button002\",\n" +
            "        \"type\":\"button\"\n" +
            "        }]\n" +
            "        }\n" +
            "    </jsonfx></xmlv>";

    static Vertx vertx;
    static WebClient client;

    @BeforeClass
    public static void start(){
        vertx = Vertx.vertx();
        client = WebClient.create(vertx);
    }

    public static void stop(){
        vertx.close();
    }

    @Test
    public void testVXML(TestContext context) throws Exception{
        final Async async = context.async();
        client.getAbs("http://39.108.96.23/static/test.xmlv")
                .send(ar ->{
                    if(ar.succeeded()){
                        System.out.println(ar.result().getHeader("Content-Type"));
                        System.out.println(ar.result().bodyAsString());

                        ObjectMapper xmlMapper = new XmlMapper();
                        try {
                            XmlV xmlV = xmlMapper.readValue(ar.result().bodyAsString(), XmlV.class);
                            System.out.println(xmlV.getJson().getJsonObject());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{

                    }
                    async.complete();
                });
    }

    @Test
    public void test() {
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
