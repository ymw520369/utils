/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.utils;

import java.io.UnsupportedEncodingException;

/**
 * Created on 2017/3/8.
 *
 * @author Alan
 * @since 1.0
 */
public final class StringUtils {

    /**
     * 以指定的分隔符连接字符串
     *
     * @param separator
     * @param strs
     * @return
     */
    public static String concat(String separator, String... strs) {
        return concatWithAffix("", "", separator, strs);
    }

    /**
     * 连接字符串
     *
     * @param prefixes  前缀
     * @param suffixes  后缀
     * @param separator 分隔符
     * @param strs      字符串数组
     * @return
     */
    public static String concatWithAffix(String prefixes, String suffixes,
                                         String separator, String... strs) {
        StringBuilder sb = new StringBuilder(prefixes != null ? prefixes : "");
        int len = strs.length;
        for (int i = 0; i < len; i++) {
            sb.append(strs[i]);
            if (i < len - 1) {
                sb.append(separator);
            }
        }
        sb.append(suffixes != null ? suffixes : "");
        return sb.toString();
    }

    public static int getStringByteLength(String str) {
        if (str == null || "".equals(str)) {
            return 0;
        }
        try {
            return str.getBytes("GBK").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Integer.MAX_VALUE;
    }
}
