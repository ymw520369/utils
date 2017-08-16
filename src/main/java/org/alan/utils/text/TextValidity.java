/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.utils.text;

import java.util.HashMap;

/**
 * Created on 2017/4/18.
 *
 * @author Alan
 * @since 1.0
 */
public class TextValidity {
    private static final char[] CHARS = {'*', '*', '*', '*', '*', '*', '*',
            '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*',};
    /* fields */
    /**
     * 允许通过的字符范围集
     */
    char[] charRangeSet;
    /**
     * 非法文字数组
     */
    String[] invalidTexts;

    /**
     * 非法文字节点map
     */
    private HashMap<String, StringNode> validityMap = new HashMap<>();

    /* properties */

    /**
     * 获得允许通过的字符范围集，两个字符为一组范围
     */
    public char[] getCharRangeSet() {
        return charRangeSet;
    }

    /**
     * 设置允许通过的字符范围集，两个字符为一组范围
     */
    public void setCharRangeSet(char[] rangeSet) {
        charRangeSet = rangeSet;
    }

    /**
     * 获得非法文字数组
     */
    public String[] getInvalidTexts() {
        return invalidTexts;
    }

    /**
     * 设置非法文字数组
     */
    public void setInvalidTexts(String[] strings) {
        invalidTexts = strings;
        // 添加到非法字符map里
        for (int i = 0; i < strings.length; i++) {
            addTextValidityMap(validityMap, strings[i]);
            if (!strings[i].toLowerCase().equals(strings[i])) {
                addTextValidityMap(validityMap, strings[i].toLowerCase());
            }
        }
    }

	/* methods */

    /**
     * 验证指定的字符串的有效性，返回验证失败的文字
     */
    public String valid(String str) {
        return valid(str, true);
    }

    public static char valid(String str, char[] charRangeSet) {
        char c;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            if (!valid(c, charRangeSet))
                return c;
        }
        return 0;
    }

    /**
     * 判断一个字符是否有效，
     *
     * @param charRangeSet 为允许通过的字符范围集 ，两个字符为一组范围，
     */
    public static boolean valid(char c, char[] charRangeSet) {
        if (charRangeSet == null)
            return true;
        for (int i = 0, n = charRangeSet.length - 1; i < n; i += 2) {
            if (c >= charRangeSet[i] && c <= charRangeSet[i + 1])
                return true;
        }
        return false;
    }

    /**
     * 验证指定的字符串的有效性，返回验证失败的文字
     *
     * @param str      给定的字符串
     * @param caseless 是否忽略大小写 true-忽略 false-区分
     * @return
     */
    public String valid(String str, boolean caseless) {
        if (str == null || str.length() < 1)
            return null;
        char c = valid(str, charRangeSet);
        if (c > 0)
            return String.valueOf(c);
        String[] strs = invalidTexts;
        if (strs == null)
            return null;
        if (caseless) {
            str = str.toLowerCase();
            for (int i = 0, n = strs.length; i < n; i++) {
                if (str.indexOf(strs[i].toLowerCase()) >= 0)
                    return strs[i];
            }
        } else {
            for (int i = 0, n = strs.length; i < n; i++) {
                if (str.indexOf(strs[i]) >= 0)
                    return strs[i];
            }
        }
        return null;
    }

    /**
     * 验证给定字符串是否含有非法字符
     *
     * @param str      给定的字符串
     * @param caseless 是否忽略大小写 true-忽略 false-区分
     * @return 验证结果
     */
    public boolean validIllegalChar(String str, boolean caseless) {
        String illegalChats = valid(str, caseless);
        if (illegalChats == null || illegalChats.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 验证给定字符串是否含有非法字符，采用默认的区分大小写的方式
     *
     * @param str 给定的字符串
     * @return 验证结果
     */
    public boolean validIllegalChar(String str) {
        return validIllegalChar(str, false);
    }

    /**
     * 根据脏字库中的内容， 使用默认的替换字符进行替换
     *
     * @param str      给定需要过滤的字符
     * @param caseless 是否忽略大小写
     * @return 过滤后的字符
     */
    public String replaceIllegalChar(String str, boolean caseless) {
        return replaceIllegalChar(str, CHARS, caseless);
    }

    /**
     * 根据脏字库中的内容，将给定字符串中的非法字符替换为给定字符
     *
     * @param str      给定需要过滤的字符
     * @param chars    给定的用于替换非法字符的字符数组
     * @param caseless 是否忽略大小写 true-忽略 false-区分
     * @return 过滤后的字符
     */
    public String replaceIllegalChar(String str, char[] chars, boolean caseless) {
        if (str == null || str.length() < 1) {
            return null;
        }
        if (invalidTexts == null || invalidTexts.length == 0) {
            return str;
        }
        if (caseless) {
            for (int i = 0, n = invalidTexts.length; i < n; i++) {
                int index = 0;
                if ((index = str.toLowerCase().indexOf(
                        invalidTexts[i].toLowerCase())) >= 0) {
                    int lenght = invalidTexts[i].length();
                    String replaceStr = new String(chars, 0, lenght);
                    str = str.replace(str.substring(index, index + lenght),
                            replaceStr);
                }
            }
        } else {
            for (int i = 0, n = invalidTexts.length; i < n; i++) {
                if (str.indexOf(invalidTexts[i]) >= 0) {
                    String replaceStr = new String(chars, 0,
                            invalidTexts[i].length());
                    str = str.replace(invalidTexts[i], replaceStr);
                }
            }
        }
        return str;
    }

    /**
     * 根据脏字树形节点算法替换脏字
     */
    public String replaceIllegalCharTree(String str, boolean caseless) {
        StringBuilder sb;
        StringBuilder initSb = new StringBuilder(str);
        if (caseless) {
            sb = new StringBuilder(str.toLowerCase());
        } else {
            sb = initSb;
        }

        for (int i = 0; i < sb.length(); i++) {
            String strIndex = String.valueOf(sb.charAt(i));
            if (validityMap.containsKey(strIndex)) {
                StringNode node = validityMap.get(strIndex);
                int length = node.getMatchLenth(sb.substring(i + 1));
                if (length > 0) {
                    initSb.replace(i, i + length, new String(CHARS, 0, length));
                    i += length - 1;
                }
            }
        }
        return initSb.toString();
    }

    /**
     * 添加非法字符map
     *
     * @param str
     */
    public void addTextValidityMap(java.util.Map<String, StringNode> validityMap, String str) {
        if (str.length() <= 0) {
            return;
        }
        int mark = 0;
        // 先获取出首节点
        if (validityMap.containsKey(String.valueOf(str.charAt(mark)))) {
            validityMap.get(String.valueOf(str.charAt(mark))).addString(
                    str.substring(1));
        } else {
            StringNode node = new StringNode();
            node.setMarkString(String.valueOf(str.charAt(mark)));
            node.addString(str.substring(1));
            validityMap.put(node.getMarkString(), node);
        }
    }

}
