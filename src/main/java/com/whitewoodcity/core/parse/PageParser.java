package com.whitewoodcity.core.parse;

import com.whitewoodcity.core.bean.VXml;

//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.Reader;
import java.io.StringReader;

/**
 * 页面解析
 */
public class PageParser {

    public PageParser() {

    }

    public <T>T parseFile(File file, Class<T> clazz){
//        try {
//            JAXBContext jaxbContext=JAXBContext.newInstance(clazz);
//            Unmarshaller us=jaxbContext.createUnmarshaller();
//            return (T) us.unmarshal(file);
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    public <T>T paresReader(Reader reader,Class<T> clazz){
//        try {
//            JAXBContext jaxbContext=JAXBContext.newInstance(clazz);
//            Unmarshaller us=jaxbContext.createUnmarshaller();
//            return (T) us.unmarshal(reader);
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
