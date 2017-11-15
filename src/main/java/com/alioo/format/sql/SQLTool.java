package com.alioo.format.sql;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuzhichong on 2016/11/3.
 */
public class SQLTool {
    /**
     * 正则替换
     *
     * @param sourceStr        原始字符串
     * @param patternString    正则表达式
     * @param groupIndex       正则匹配的结果子集下标
     * @param subPatternString 正则表达式patternString匹配的指定下标groupIndex的字符，使用新的正则subPatternString匹配并替换成replaceStr
     * @param replaceStr       最终替换成的字符串
     * @return
     */
    public static String secondLevelReplace(String sourceStr, String patternString, int groupIndex, String subPatternString, String replaceStr) {
        System.out.println("sourceStr[" + sourceStr + "]");
        System.out.println("matches[" + patternString + "]");

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(sourceStr);

        //两个方法：appendReplacement, appendTail
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String str = matcher.group(0);
            String substr = matcher.group(groupIndex);
//            tmpgroupIndex=tmpgroupIndex=tmpgroupIndex.replaceAll("\\s","");
            String replace = substr.replaceAll(subPatternString, replaceStr);
            String newstr = thirdreplace(str, substr, replace);
            System.out.println("after group(0)[" + str + "]");
            System.out.println("after group(" + groupIndex + ")[" + substr + "]");
            System.out.println("after group(" + groupIndex + "),replaceAllByPatternString(" + subPatternString + "),result[" + replace + "]");
            System.out.println("after group(0),last result[" + newstr + "]");
            System.out.println("");
            matcher.appendReplacement(sb, newstr);
//            System.out.println("subgroup.end>>>" + sb.toString());
        }
        matcher.appendTail(sb);
        System.out.println("after secondLevelReplace,result[" + sb.toString() + "]");
        return sb.toString();
    }

    /**
     * 字符串替换
     *
     * @param str           str一定要包含substr
     * @param substr
     * @param substrreplace str中substr的部分会被替换成substrreplace
     * @return
     */
    public static String thirdreplace(String str, String substr, String substrreplace) {
        int idx = str.indexOf(substr);
        int lastidx = idx + substr.length();
        StringBuffer sb = new StringBuffer();
        sb.append(str.substring(0, idx)).append(substrreplace).append(str.substring(lastidx, str.length()));
        return sb.toString();
    }


    /**
     * 判断字符c在字符串mystr中出现的次数
     *
     * @param mystr
     * @param c
     * @return
     */
    public static int cishu(String mystr, char c) {
        int count = 0;
        for (int i = 0; i < mystr.length(); i++) {
            if (mystr.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

}
