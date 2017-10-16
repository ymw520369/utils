/**
 * Copyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.field;

/**
 * 类说明：双浮点数域
 * 
 * @version 1.0
 * @author zminleo <zmin@seasky.cn>
 */

public final class DoubleField extends FieldObject implements Comparable {

	/* fields */
	/** 值 */
	public double value;

	/* methods */
	/** 获得值 */
	public Object getValue() {
		return new Double(value);
	}

	/** 比较方法 */
	public int compareTo(Object obj) {
		if (!(obj instanceof DoubleField))
			return COMP_GRTR;
		double v = ((DoubleField) obj).value;
		if (value > v)
			return COMP_GRTR;
		if (value < v)
			return COMP_LESS;
		return COMP_EQUAL;
	}

}