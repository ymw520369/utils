/**
 * Copyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.field;

/**
 * 绫昏鏄庯細鍩熷璞�
 * 
 * @version 1.0
 * @author zminleo <zmin@seasky.cn>
 */

public abstract class FieldObject {
	public final int COMP_GRTR = 1, COMP_EQUAL = 0, COMP_LESS = -1;
	/* fields */
	/** 鍩熷悕绉�*/
	public String name;

	/* methods */
	/** 鑾峰緱鍊�*/
	public abstract Object getValue();

}