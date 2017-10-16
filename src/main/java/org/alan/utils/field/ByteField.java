/**
 * Copyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.field;

/**
 * 类说明：字节数域
 * 
 * @version 1.0
 * @author zminleo <zmin@seasky.cn>
 */

public final class ByteField extends FieldObject implements Comparable {

	/* fields */
	/** 值 */
	public byte value;

	/* methods */
	/** 获得值 */
	public Object getValue() {
		return new Byte(value);
	}

	/** 比较方法 */
	public int compareTo(Object obj) {
		if (!(obj instanceof ByteField))
			return COMP_GRTR;
		byte v = ((ByteField) obj).value;
		if (value > v)
			return COMP_GRTR;
		if (value < v)
			return COMP_LESS;
		return COMP_EQUAL;
	}

}