/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 *
 * 2016年9月9日 	
 */
package org.alan.utils;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * 
 * @scene 1.0
 * 
 * @author Alan
 *
 */
public class CopyOnWriteArrayListTest {

	public static void main(String[] args) {
		List<String> lst = new CopyOnWriteArrayList<String>();
		lst.add("123");
		lst.add("456");
		lst.add("789");
		Iterator<String> iter = lst.iterator();
		while (iter.hasNext()) {
			iter.next();
			lst.remove("123");
		}
	}
}
