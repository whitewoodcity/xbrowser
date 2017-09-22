package com.whitewoodcity.core.parse;

import com.whitewoodcity.core.bean.CSS;
import com.whitewoodcity.core.bean.Script;
import com.whitewoodcity.core.bean.VXml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

public class VXmlParser {


    public void parse(File file){
        try {
            JAXBContext jaxbContext=JAXBContext.newInstance(VXml.class);
            Unmarshaller us=jaxbContext.createUnmarshaller();
            VXml vXml= (VXml) us.unmarshal(file);
            List<CSS> csses=vXml.getCsses();
            CSS css=csses.get(0);
            System.out.println(css);
            System.out.println(vXml.getScripts().get(0));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void pares(Reader reader){
        try {
            JAXBContext jaxbContext=JAXBContext.newInstance(VXml.class);
            Unmarshaller us=jaxbContext.createUnmarshaller();
            VXml vXml= (VXml) us.unmarshal(reader);
            List<CSS> csses=vXml.getCsses();
            CSS css=csses.get(0);
            System.out.println(css);
            System.out.println(vXml.getScripts().get(0));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
