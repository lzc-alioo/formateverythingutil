package com.alioo.format.module;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by liuzhichong on 2016/8/30.
 */
public class XMLFormatModule implements FormatModule {
//    private static final Logger logger = LoggerFactory.getLogger(XMLFormatModulel.class);


    /**
     * @param str
     * @return
     */
    public String format(String str) {
        try {
            final Document document = parseXmlFile(str);
            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(120);
            format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    private Document parseXmlFile(String str) throws ParserConfigurationException, IOException, SAXException {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(str));
            return db.parse(is);
    }

    public static void main(String[] args) throws Exception{
//        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><PARAM><DBID>35</DBID><SEQUENCE>atgtca</SEQUENCE><MAXNS>10</MAXNS><MINIDENTITIES>90</MINIDENTITIES><MAXEVALUE>10</MAXEVALUE><USERNAME>admin</USERNAME><PASSWORD>111111</PASSWORD><TYPE>P</TYPE><RETURN_TYPE>2</RETURN_TYPE></PARAM>";//未格式化前的xml
//        String s = "<PARAM><DBID>35</DBID><SEQUENCE>atgtca</SEQUENCE><MAXNS>10</MAXNS><MINIDENTITIES>90</MINIDENTITIES><MAXEVALUE>10</MAXEVALUE><USERNAME>admin</USERNAME><PASSWORD>111111</PASSWORD><TYPE>P</TYPE><RETURN_TYPE>2</RETURN_TYPE></PARAM>";//未格式化前的xml
        String s = "<param><DBID>35</DBID><SEQUENCE>atgtca</SEQUENCE><MAXNS>10</MAXNS><MINIDENTITIES>90</MINIDENTITIES><MAXEVALUE>10</MAXEVALUE><USERNAME>admin</USERNAME><PASSWORD>111111</PASSWORD" +
                "><TYPE>P</TYPE><RETURN_TYPE>2</RETURN_TYPE></PARAM>";//未格式化前的xml
        System.out.println(new XMLFormatModule().format(s));
//
    }

}
