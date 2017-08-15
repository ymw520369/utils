/**
 * MathHelper.java 2013-6-8
 */
package org.alan.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 数学相关公式计算，随机数生成工具
 * 
 * @author 杨明伟
 * @version 1.0
 * @All rights reserved.
 */
public final class MathHelper {

	/**
	 * 该方法从给定的集合中随机选取指定的个数，如果指定的个数超过集合长度，返回原集合中所有元素
	 * 
	 * @param sourceList
	 *            给定的集合
	 * @param number
	 *            需要随机选取的数量
	 * @return 随机选取后的集合（新的集合对象，源集合不变）
	 */
	public static List randomDrawByList(List sourceList, int number) {
		List<Object> tempSourceList = new ArrayList<Object>(sourceList);
		int size = tempSourceList.size();
		if (number >= size || number <= 0) {
			return tempSourceList;
		}
		for (int i = size; i > number; i--) {
			int r = (int) (Math.random() * tempSourceList.size());
			tempSourceList.remove(r);
		}
		return tempSourceList;
	}

}
