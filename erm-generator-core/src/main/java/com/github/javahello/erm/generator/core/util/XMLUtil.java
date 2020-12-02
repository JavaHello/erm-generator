package com.github.javahello.erm.generator.core.util;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * 
 * @author kaiv2
 *
 */
public abstract class XMLUtil {

    /**
     * 将XML文件转换成对象
     * 
     * @throws JAXBException         解析失败抛出
     * @throws FileNotFoundException 文件未找到抛出
     */
    public static <T> T convertXMLFileToObject(Class<T> clazz, String xmlPath)
            throws JAXBException, FileNotFoundException {

        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        FileReader fr = null;
        fr = new FileReader(xmlPath);
        @SuppressWarnings("unchecked")
        T xmlObject = (T) unmarshaller.unmarshal(fr);

        return xmlObject;
    }
}
