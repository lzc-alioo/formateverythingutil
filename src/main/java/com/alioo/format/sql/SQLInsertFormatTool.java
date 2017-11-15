package com.alioo.format.sql;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class SQLInsertFormatTool {

    /**
     * sql(insert) format。
     *
     * @param sql
     * @return 格式化的sql字符串。
     */
    public static String format(String sql) {
//        sql = StringTool.secondLevelReplace(sql, "COMMENT '(.+?)'", 1, "\\s", "");//把在单引号(注释)中的空格，去掉
        int i = 0;
        int lastfind = 0;
        StringBuffer sb = new StringBuffer();
        while (i < sql.length()) {
            char c = sql.charAt(i);
            sb.append(c);
            i++;
            if (c == ',') {  //原则上针对','后面追加换行符，另外下一行增加缩进(4个空格)
                String tmpStr = sql.substring(lastfind, i).toLowerCase();
                //解决注释中含有","的情况 && 解决浮点数小数位数中含有","的情况 && 解决 联合索引中含有","的情况，
                // 判断2次","中"'"的个数，
                int count = SQLTool.cishu(tmpStr, '\'');
                if (count == 0) {
                    //如果2次","中"'"的个数为0 进一步判断2次","中是否含有decimal字符串，如果没有则添加换行符，否则需要添加换行符
                    boolean flag2 = tmpStr.contains("decimal");
                    if (flag2) {
                        continue;
                    }
                    boolean flag3 = tmpStr.contains("key");
                    if (flag3) {
                        boolean flag4 = tmpStr.contains("(");
                        if (!flag4) {
                            continue;
                        }
                        boolean flag5 = tmpStr.contains(")");
                        if (!flag5) {
                            continue;
                        }
                    }
                    sb.append("\n    ");
                    lastfind = i;//记录最近一次换行后","的位置
                } else if (count == 1) {
                    //如果2次","中"'"的个数为1
                } else {
                    //如果2次","中"'"的个数为2
                    sb.append("\n    ");
                    lastfind = i;//记录最近一次换行后","的位置
                }
            }

        }
        //针对其中的第1个追加换行符
        int startIdx = sb.indexOf("(");
        if (startIdx > -1) {
            sb.insert(startIdx + 1, "\n    ");
        }
        int valuesIdx = sb.toString().toLowerCase().indexOf("values ");
        if (valuesIdx > -1) {
            sb.insert(valuesIdx, "\n"); // values关键字开始处追加一个换行符
            sb.insert(valuesIdx + 8, "\n   "); //values关键字结尾追加一个换行符
//            sb.insert(valuesIdx + 10, "\n    "); //values关键字之后的一个左括号追加一个换行符
        }
        int valuesNewIdx=sb.toString().toLowerCase().indexOf("values ");
        String valueslast=sb.toString().substring(valuesNewIdx);
        Pattern pattern=Pattern.compile("\\([.\n]*\\)");
        Matcher matcher=pattern.matcher(valueslast);
        while(matcher.find()){
            String str = matcher.group(0);
            System.out.println("+++" + str);
        }

        //TODO:如下这种情况还需要进一步格式化下
//        (NULL,
//        '8727d831-9f93-11e3-9969-1260d495f62a',
//        '2014-06-28 01:43:55.000000',
//        now(),
//        '0',
//        1403891035,
//        'PSBC',
//        NULL,
//        '中国邮政储蓄银行',
//        'Postal Savings Bank of China',
//        NULL,
//        NULL,
//        NULL,
//        NULL),
//        (NULL,
//        '8727d831-9f93-11e3-9969-1260d495f62a',
//        '2014-06-28 01:43:55.000000',
//        now(),
//        '0',
//        1403891035,
//        'CNCB',
//        NULL,
//        '中信银行',
//        'China CITIC Bank',
//        NULL,
//        NULL,
//        NULL,
//        NULL),



//        int endIdx = sb.lastIndexOf(")");
//        if (endIdx > -1) {
//            sb.insert(endIdx, "\n");
//        }
//        int selectIdx = sb.toString().toLowerCase().indexOf("select ");
//        if (selectIdx > -1) {
//            sb.insert(selectIdx + 7, "\n    "); //select关键字结尾追加一个换行符
//        }
//        int fromIdx = sb.toString().toLowerCase().indexOf("from ");
//        if (fromIdx > -1) {
//            sb.insert(fromIdx + 5, "\n    ");//from关键字结尾追加一个换行符
//            sb.insert(fromIdx, "\n"); //from关键字前面追加一个换行符
//        }
//        int whereIdx = sb.toString().toLowerCase().indexOf("where ");
//        if (whereIdx > -1) {
//            sb.insert(whereIdx + 6, "\n    ");//where关键字结尾追加一个换行符
//            sb.insert(whereIdx, "\n"); //where关键字前面追加一个换行符
//        }
//        System.out.println(sb);
        return sb.toString();
    }

}
