
package org.alan.utils.xml;


import org.alan.utils.FileHelper;
import org.alan.utils.script.ScriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类说明：脚本分析器
 *
 * @version 1.0
 */

public class ScriptParser implements Parser {

	/* static fields */
    /**
     * 缺省的脚本语言
     */
    public static final String LANGUAGE = "language";

    /**
     * 日志记录
     */
    private static final Logger log = LoggerFactory.getLogger(ScriptParser.class);

	/* methods */

    /**
     * 分析方法
     */
    public Object parse(Element el, XmlContext context, Object arg) {
        String language = el.getAttribute(LANGUAGE);
        ScriptExecutor executor = context.getExecutor(language);
        if (executor == null)
            return null;
        String src = el.getAttribute("src");
        if (src != null) {
            String data = FileHelper.readFile(src);
            if (data == null) {
                if (log.isInfoEnabled())
                    log.info("parse error, file read fail, src=" + src);
                return executor;
            }
            try {
                executor.execute(data, src, 1);
            } catch (Exception e) {
                if (log.isWarnEnabled())
                    log.warn("parse error, src execute, " + el, e);
            }
            return null;
        }
        String name = el.getAttribute("name");
        try {
            for (int i = 0, n = el.getContentCount(); i < n; i++) {
                if (el.getType(i) != XmlReader.COMMENT)
                    continue;
                executor.execute((String) el.getContent(i), name, 1);
            }
        } catch (Exception e) {
            if (log.isWarnEnabled())
                log.warn("parse error, content execute, " + el, e);
        }
        return null;
    }

}