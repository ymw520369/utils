/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 *
 * 2016年5月25日 	
 */
package org.alan.utils;

/**
 * @author Alan
 * 
 * @version 1.0
 *
 */
public class ArrayUtils {

	public static String toString(Object[] a, String separator) {
		if (a == null) {
			return null;
		}

		int iMax = a.length - 1;
		if (iMax == -1) {
			return "";
		}

		StringBuilder b = new StringBuilder();
		for (int i = 0;; i++) {
			b.append(String.valueOf(a[i]));
			if (i == iMax) {
				return b.toString();
			}
			b.append(separator);
		}
	}

}
