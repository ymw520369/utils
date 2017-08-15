package org.alan.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * zip文件工具类
 */
public class ZipUtil {

	// public static void main(String[] args) {
	// ZipUtil.zipFile("C:\\test\\20150503", "C:\\test\\20150503.zip");
	// }

	/** 将某个baseDir压缩为指定名字绝对路径的fileName的zip */
	public static void zipFile(String baseDir, String fileName) {
		try {
			ArrayList<File> fileList = getSubFiles(new File(baseDir));// 获取该目录下所有文件和文件夹
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(
					fileName));
			ZipEntry ze = null;
			byte[] buf = new byte[1024];
			int readLen = 0;
			for (int i = 0; i < fileList.size(); i++) {
				File f = (File) fileList.get(i);
				ze = new ZipEntry(getAbsFileName(baseDir, f));
				ze.setSize(f.length());
				ze.setTime(f.lastModified());
				zos.putNextEntry(ze);
				InputStream is = new BufferedInputStream(new FileInputStream(f));
				while ((readLen = is.read(buf, 0, 1024)) != -1) {
					zos.write(buf, 0, readLen);
				}
				is.close();
			}
			zos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getAbsFileName(String baseDir, File realFileName) {
		File real = realFileName;
		File base = new File(baseDir);
		String ret = real.getName();
		while (true) {
			real = real.getParentFile();
			if (real == null)
				break;
			if (real.equals(base))
				break;
			else
				ret = real.getName() + "/" + ret;
		}
		return ret;
	}

	/** 获取子文件和子文件夹 */
	private static ArrayList<File> getSubFiles(File baseDir) {
		ArrayList<File> ret = new java.util.ArrayList<File>();
		if (baseDir.isDirectory()) {
			File[] tmp = baseDir.listFiles();
			if (tmp != null) {
				for (int i = 0; i < tmp.length; i++) {
					if (tmp[i].isFile())
						ret.add(tmp[i]);
					if (tmp[i].isDirectory())
						ret.addAll(getSubFiles(tmp[i]));
				}
			}
		} else {
			ret.add(baseDir);
		}
		return ret;
	}

}
