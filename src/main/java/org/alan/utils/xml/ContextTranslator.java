
package org.alan.utils.xml;

/**
 * 类说明：应用环境中的文字转换器
 *
 * @version 1.0
 */

public class ContextTranslator {

	/* fields */
    /**
     * 应用环境
     */
    Context context;

	/* constructors */

    /**
     * 构造指定应用环境的文字转换器
     */
    public ContextTranslator(Context context) {
        this.context = context;
    }

	/* properties */

    /**
     * 获得应用环境
     */
    public Context getContext() {
        return context;
    }

    /**
     * 获得指定的转换文字
     */
    public String getText(String str) {
        return (String) (context.get(str));
    }

    /**
     * 添加指定的转换文字
     */
    public void addText(String str, String text) {
        context.set(str, text);
    }

    /**
     * 移除指定的转换文字
     */
    public String removeText(String str) {
        return (String) (context.remove(str));
    }

}