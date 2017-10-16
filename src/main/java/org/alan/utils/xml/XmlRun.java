/**
 * Copyright 2001 by seasky <www.seasky.cn>.
 */

package org.alan.utils.xml;

/**
 * 类说明：xml运行器
 * 
 * @version 1.0
 * @author zminleo <zmin@seasky.cn>
 */

public class XmlRun implements Runnable {

	/* fields */
	/** xml环境 */
	XmlContext context;
	/** 元素 */
	Element element;

	/* properties */
	/** 获得xml环境 */
	public XmlContext getXmlContext() {
		return context;
	}

	/** 设置xml环境 */
	public void setXmlContext(XmlContext c) {
		context = c;
	}

	/** 获得元素 */
	public Element getElement() {
		return element;
	}

	/** 设置元素 */
	public void setElement(Element e) {
		element = e;
	}

	/* methods */
	public void run() {
		context.parse(element);
	}

}