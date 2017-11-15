package com.alioo.format.sql;

/**
 *
 */
public class SQLSelectFormatTool {

    /**
     * sql(select) format。
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
                    boolean flag3 = tmpStr.contains("mod");
                    if (flag3) {
                        continue;
                    }
                    boolean flag4 = tmpStr.contains("(");
                    boolean flag5 = tmpStr.contains(")");
                    if (flag4 && !flag5) {
                        continue;
                    }

                    sb.append("\n    "); //如果都没有拦截到，则追加换行符
                    lastfind = i;//记录最近一次换行后","的位置
                    continue;
                } else if (count == 1) {
                    //如果2次","中"'"的个数为1
                } else {
                    //如果2次","中"'"的个数为2
                    sb.append("\n    ");
                    lastfind = i;//记录最近一次换行后","的位置
                }
            }

        }
//        //针对其中的第1个(和最后1个）追加换行符
//        int startIdx = sb.indexOf("(");
//        if (startIdx > -1) {
//            sb.insert(startIdx + 1, "\n    ");
//        }
//        int endIdx = sb.lastIndexOf(")");
//        if (endIdx > -1) {
//            sb.insert(endIdx, "\n");
//        }
        int selectIdx = sb.toString().toLowerCase().indexOf("select ");
        if (selectIdx > -1) {
            sb.insert(selectIdx + 7, "\n    "); //select关键字结尾追加一个换行符
        }
        int fromIdx = sb.toString().toLowerCase().indexOf("from ");
        if (fromIdx > -1) {
            sb.insert(fromIdx + 5, "\n    ");//from关键字结尾追加一个换行符
            sb.insert(fromIdx, "\n"); //from关键字前面追加一个换行符
        }
        int whereIdx = sb.toString().toLowerCase().indexOf("where ");
        if (whereIdx > -1) {
            sb.insert(whereIdx + 6, "\n    ");//where关键字结尾追加一个换行符
            sb.insert(whereIdx, "\n"); //where关键字前面追加一个换行符
        }
        System.out.println(sb);
        return sb.toString();
    }

}
