/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 *
 * 2016年4月27日 	
 */
package org.alan.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Alan
 * 
 * @version 1.0
 *
 *          脚本辅助工具
 */
public class ScriptUtils {


	/**
	 * 执行给定的命令
	 * 
	 * @param cmd
	 * @return
	 * @throws IOException
	 */
	public static String exec(String cmd) throws IOException {
		Process process = Runtime.getRuntime().exec(cmd);
		InputStream is = process.getInputStream();
		InputStream err = process.getErrorStream();
		return read(is) + read(err);
	}

	public static String exec(String[] cmd) throws IOException {
		Process process = Runtime.getRuntime().exec(cmd);
		InputStream is = process.getInputStream();
		InputStream err = process.getErrorStream();
		return read(is) + read(err);
	}

	public static String read(InputStream is) throws IOException {
		InputStreamReader inReader = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(inReader);
		StringBuilder sb = new StringBuilder();
		char[] charBuffer = new char[1024];
		int n = 0;
		while ((n = br.read(charBuffer)) != -1) {
			sb.append(charBuffer, 0, n);
		}
		return sb.toString();
	}

}
