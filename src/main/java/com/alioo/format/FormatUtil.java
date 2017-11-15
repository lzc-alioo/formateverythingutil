package com.alioo.format;

import com.alioo.format.module.FormatModule;
import com.alioo.format.module.JSONFormatModule;
import com.alioo.format.module.XMLFormatModule;
import com.alioo.format.module.SQLFormatModule;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 程序入口
 *
 * Created by liuzhichong on 2016/9/20.
 */
public class FormatUtil {

    private static Map<String, FormatModule> formatModuleMap = new HashMap<String, FormatModule>();
    static {
        formatModuleMap.put("jsonFormatModule",new JSONFormatModule());
        formatModuleMap.put("sqlFormatModule",new SQLFormatModule());
        formatModuleMap.put("xmlFormatModule",new XMLFormatModule());
    }

    /**
     * 格式化
     *
     * @param source
     * @return target
     */
    public static String format(String source) {
        String formatModuleStr = getFormatModuleStr(source);
        FormatModule formatModule = formatModuleMap.get(formatModuleStr);
        String target = formatModule.format(source);


        return target;
    }

    private static String getFormatModuleStr(String str) {
        String formatBeanStr = "jsonFormatModule";
        if (isSQL(str)) {
            formatBeanStr = "sqlFormatModule";
        }else if(isXML(str)){
            formatBeanStr = "xmlFormatModule";
        }
        return formatBeanStr;
    }

    private static boolean isSQL(String str) {
        str = str.toLowerCase();
        List<String> list = new ArrayList<String>();
        list.add("select ");
        list.add("delete from ");
        list.add("update ");
        list.add("inset into ");

        str = str.toLowerCase(); //转成小写后再比较
        for (int i = 0; i < list.size(); i++) {
            String tmp = list.get(i);
            if (str.contains(tmp)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isXML(String str) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(str));
            db.parse(is);
            return true;
        } catch (Exception e) {
            return false;
        }


    }

}
