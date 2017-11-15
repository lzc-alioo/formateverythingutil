package com.alioo.format;

import org.junit.Test;

/**
 * Created by alioo on 2017/11/4.
 */

public class FormatTest {


    @Test
    public void aaa(){

        System.out.println("lzc...");
        String s = "<param><DBID>35</DBID><SEQUENCE>atgtca</SEQUENCE><MAXNS>10</MAXNS><MINIDENTITIES>90</MINIDENTITIES><MAXEVALUE>10</MAXEVALUE><USERNAME>admin</USERNAME><PASSWORD>111111</PASSWORD" +
                "><TYPE>P</TYPE><RETURN_TYPE>2</RETURN_TYPE></param>";//未格式化前的xml

        String target=FormatUtil.format(s);
        System.out.println("targer="+target);
    }

}
