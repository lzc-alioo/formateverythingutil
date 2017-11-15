package com.alioo.format.module;


import com.alioo.format.json.JsonFormatTool;

/**
 * Created by liuzhichong on 2016/8/30.
 */
public class JSONFormatModule implements FormatModule {

    /**
     * @param str
     * @return
     */
    public String format(String str) {

        String result = JsonFormatTool.formatJson(str);

        return result;
    }


}
