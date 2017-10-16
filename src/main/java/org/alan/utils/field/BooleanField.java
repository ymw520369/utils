/**
 * Copyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.field;


/**
 * 类说明：布尔数域
 * 
 * @version 1.0
 * @author zminleo <zmin@seasky.cn>
 */

public final class BooleanField extends FieldObject implements Comparable {

	/* fields */
	/** 值 */
	public boolean value;

	/* methods */
	/** 获得值 */
	public Object getValue() {
		return new Boolean(value);
	}

	/** 比较方法 */
	public int compareTo(Object obj) {
		if (!(obj instanceof BooleanField))
			return COMP_GRTR;
		boolean v = ((BooleanField) obj).value;
		if (value)
			return COMP_GRTR;
		if (v)
			return COMP_GRTR;
		return COMP_EQUAL;
	}

}