
package org.alan.utils.xml;

import org.alan.utils.FileHelper;
import org.alan.utils.script.BeanShellExecutor;
import org.alan.utils.script.JavaScriptExecutor;
import org.alan.utils.script.ScriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 类说明：xml环境，类名加载方式获得指定名称的分析器
 *
 * @version 1.0
 */

public class ClassNameXmlContext implements XmlContext {

	/* static fields */
    /**
     * 本地引用前缀
     */
    public static final String LOCAL_REF_PREFIX = "__";
    /**
     * 缺省的脚本语言
     */
    public static final String DEFAULT_SCRIPT = "BeanShell";
    /**
     * 默认的分析器前缀数组
     */
    public static final String[] PARSER_PREFIXS = {"zNet.xml."};
    /**
     * 默认的分析器前缀数组
     */
    public static final String PARSER_SUFFIX = "Parser";

    /**
     * 日志记录
     */
    private static final Logger log = LoggerFactory
            .getLogger(ClassNameXmlContext.class);

	/* fields */
    /**
     * 父环境
     */
    XmlContext parent;
    /**
     * 引用表
     */
    Map refMap;
    /**
     * 本地引用前缀
     */
    String localRefPrefix;

    /**
     * 分析器列表
     */
    Map parserMap;
    /**
     * 分析器前缀数组
     */
    String[] parserPrefixs;
    /**
     * 分析器后缀
     */
    String parserSuffix;
    /**
     * 类加载器
     */
    ClassLoader loader;

    /**
     * 是否忽略大小写
     */
    boolean ignoreCase;
    /**
     * 是否警告在设置对象时出现同名ID
     */
    boolean warnSameId;

    /**
     * 脚本执行器列表
     */
    Map executorMap;

	/* constructors */

    /**
     * 用指定的父环境构造一个分析环境
     */
    public ClassNameXmlContext(XmlContext parent) {
        this.parent = parent;
        refMap = new HashMap();
        localRefPrefix = LOCAL_REF_PREFIX;
        parserMap = new HashMap();
        parserPrefixs = PARSER_PREFIXS;
        parserSuffix = PARSER_SUFFIX;
        ignoreCase = true;
        executorMap = new HashMap();
    }

	/* properties */

    /**
     * 获得父环境
     */
    public Context getParent() {
        return parent;
    }

    /**
     * 获得父环境
     */
    public XmlContext getXmlParent() {
        return parent;
    }

    /**
     * 获得本地引用前缀
     */
    public String getLocalRefPrefix() {
        return localRefPrefix;
    }

    /**
     * 设置本地引用前缀
     */
    public void setLocalRefPrefix(String prefix) {
        localRefPrefix = prefix;
    }

    /**
     * 获得分析器前缀数组
     */
    public String[] getParserPrefixs() {
        return parserPrefixs;
    }

    /**
     * 设置分析器前缀
     */
    public void setParserPrefixs(String[] prefixs) {
        parserPrefixs = prefixs;
    }

    /**
     * 获得分析器后缀
     */
    public String getParserSuffix() {
        return parserSuffix;
    }

    /**
     * 设置分析器后缀
     */
    public void setParserSuffix(String suffix) {
        parserSuffix = suffix;
    }

    /**
     * 获得类加载器
     */
    public ClassLoader getClassLoader() {
        return loader;
    }

    /**
     * 设置类加载器
     */
    public void setClassLoader(ClassLoader loader) {
        this.loader = loader;
    }

    /**
     * 判断是否忽略大小写
     */
    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    /**
     * 设置是否忽略大小写
     */
    public void setIgnoreCase(boolean b) {
        ignoreCase = b;
    }

    /**
     * 判断是否警告同名ID
     */
    public boolean isWarnSameId() {
        return warnSameId;
    }

    /**
     * 设置是否警告同名ID
     */
    public void setWarnSameId(boolean b) {
        warnSameId = b;
    }

	/* methods */

    /**
     * 列出全部的引用名
     */
    public String[] list() {
        synchronized (this) {
            String[] strs = new String[refMap.size()];
            refMap.keySet().toArray(strs);
            return strs;
        }
    }

    /**
     * 获得指定引用的对象
     */
    public Object get(String name) {
        if (name == null)
            return null;
        Item item;
        synchronized (this) {
            item = (Item) refMap.get(name);
        }
        if (item != null) {
            synchronized (item) {
                if (item.load < LAZY)
                    return item.obj;
                Parser p = getParser(item.el.getName());
                Object obj = (p != null) ? p.parse(item.el, this, this) : null;
                if (item.load == LAZY) {
                    item.load = 0;
                    item.el = null;
                    item.obj = obj;
                }
                return obj;
            }
        }
        return (parent != null) ? parent.get(name) : null;
    }

    /**
     * 获得指定引用的对象工厂，对象工厂使用配置参数获得对象实例
     */
    public Object get(String name, Object parameter) {
        Object obj = get(name);
        if (obj == null || !(obj instanceof ObjectFactory))
            return null;
        return ((ObjectFactory) obj).getInstance(parameter);
    }

    /**
     * 设置对象
     */
    public Object set(String name, Object value) {
        if (name == null)
            return null;
        Item old;
        synchronized (this) {
            Item item = new Item();
            item.obj = value;
            old = (Item) refMap.put(name.intern(), item);
        }
        setReference(name, value);
        if (old == null)
            return null;
        return old.load == 0 ? old.obj : old.el;
    }

    /**
     * 移除指定引用的对象
     */
    public Object remove(String name) {
        if (name == null)
            return null;
        Item old;
        synchronized (this) {
            old = (Item) refMap.get(name);
        }
        return (old != null) ? old.obj : null;
    }

    /**
     * 设置加载方式所加载的对象描述
     */
    public Object set(String name, Element el, int load) {
        if (name == null)
            return null;
        Item old;
        synchronized (this) {
            Item item = new Item();
            item.load = load;
            item.el = el;
            old = (Item) refMap.put(name.intern(), item);
        }
        if (old == null)
            return null;
        return old.load == 0 ? old.obj : old.el;
    }

    /**
     * 设置脚本变量方法
     */
    public synchronized void setReference(String name, Object value) {
        if (name == null)
            return;
        ScriptExecutor executor;
        Iterator it = executorMap.values().iterator();
        while (it.hasNext()) {
            executor = (ScriptExecutor) (it.next());
            executor.set(name, value);
        }
    }

    /**
     * 获得指定元素的分析器
     */
    public Parser getParser(String name) {
        if (name == null || name.length() == 0)
            return null;
        if (ignoreCase)
            name = name.toLowerCase();
        Parser p;
        synchronized (this) {
            p = (Parser) (parserMap.get(name));
            if (p != null)
                return p;
        }
        if (parent != null) {
            p = parent.getParser(name);
            if (p != null)
                return p;
        }
        synchronized (this) {
            p = (Parser) (parserMap.get(name));
            if (p != null)
                return p;
            try {
                Class c = getClass(name);
                if (c == null)
                    return null;
                p = (Parser) (c.newInstance());
                parserMap.put(name.intern(), p);
                return p;
            } catch (Exception e) {
                if (log.isWarnEnabled())
                    log.warn("getParser error, name=" + name, e);
                return null;
            }
        }
    }

    /**
     * 设置指定元素的分析器
     */
    public void setParser(String name, Parser parser) {
        if (name == null || name.length() == 0)
            return;
        if (ignoreCase)
            name = name.toLowerCase();
        synchronized (this) {
            parserMap.put(name.intern(), parser);
        }
    }

    /**
     * 检查是否存在指定元素的分析器
     */
    public Parser checkParser(String name) {
        if (name == null || name.length() == 0)
            return null;
        if (ignoreCase)
            name = name.toLowerCase();
        synchronized (this) {
            Parser p = (Parser) (parserMap.get(name));
            if (p != null)
                return p;
        }
        return (parent != null) ? parent.checkParser(name) : null;
    }

    /**
     * 获得相应的类
     */
    public Class getClass(String name) throws ClassNotFoundException {
        ClassNotFoundException ex = null;
        String[] prefixs = parserPrefixs;
        if (name.indexOf('.') < 0 && prefixs != null) {
            for (int i = 0; i < prefixs.length; i++) {
                try {
                    return getClass(name, prefixs[i], parserSuffix);
                } catch (ClassNotFoundException e) {
                    ex = e;
                }
            }
            throw ex;
        }
        return getClass(name, null, parserSuffix);
    }

    /**
     * 获得相应的类
     */
    public Class getClass(String name, String prefix, String suffix)
            throws ClassNotFoundException {
        if (name.indexOf('.') < 0 && prefix != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(prefix);
            sb.append(name);
            sb.append(suffix);
            name = sb.toString();
        } else if (suffix != null) {
            name = name + suffix;
        }
        return (loader != null) ? loader.loadClass(name) : Class.forName(name);
    }

    /**
     * 获得指定语言的脚本执行器
     */
    public synchronized ScriptExecutor getExecutor(String language) {
        if (language == null)
            language = DEFAULT_SCRIPT;
        ScriptExecutor se = (ScriptExecutor) (executorMap.get(language));
        if (se == null) {
            if (DEFAULT_SCRIPT.equals(language))
                se = new BeanShellExecutor();
            else if ("JavaScript".equals(language))
                se = new JavaScriptExecutor();
            else
                return null;
            executorMap.put(language, se);
            setReferences(se);
        }
        return se;
    }

    /**
     * 设置脚本执行器的引用对象
     */
    void setReferences(ScriptExecutor executor) {
        Map.Entry entry;
        String name;
        Item item;
        Iterator it = refMap.entrySet().iterator();
        while (it.hasNext()) {
            entry = (Map.Entry) (it.next());
            name = (String) entry.getKey();
            item = (Item) entry.getValue();
            executor.set(name, item.obj);
        }
    }

    /**
     * 分析指定uri的xml文档
     */
    public void parse(String src) {
        if (log.isDebugEnabled())
            log.debug("parse, src=" + src);
        byte[] data = FileHelper.read(src);
        if (data == null)
            throw new IllegalArgumentException(getClass().getName()
                    + " parse, open file fail, src=" + src);
        Document doc = new Document();
        XmlReader xmlReader = new ZXmlReader();
        xmlReader.setInput(new ByteArrayInputStream(data), null);
        doc.read(xmlReader);
        parse(doc);
    }

    /**
     * 分析xml文档
     */
    public void parse(Document doc) {
        Parser parser = getParser(doc.getRootElement().getName());
        if (parser == null)
            return;
        parser.parse(doc.getRootElement(), this, null);
        // 清除本地引用
        Map.Entry entry;
        String key;
        synchronized (this) {
            Iterator it = refMap.entrySet().iterator();
            while (it.hasNext()) {
                entry = (Map.Entry) (it.next());
                key = (String) (entry.getKey());
                if (key.startsWith(localRefPrefix))
                    it.remove();
            }
        }
    }

    /**
     * 分析xml元素
     */
    public void parse(Element e) {
        Parser parser = getParser(e.getName());
        if (parser == null)
            return;
        parser.parse(e, this, null);
    }

    /**
     * 清理对象
     */
    public void clear() {
        synchronized (this) {
            refMap.clear();
        }
        clearScript();
    }

    /**
     * 清理脚本方法
     */
    public synchronized void clearScript() {
        ScriptExecutor executor;
        Iterator it = executorMap.values().iterator();
        while (it.hasNext()) {
            executor = (ScriptExecutor) (it.next());
            executor.exit();
        }
        executorMap.clear();
    }

    /* common methods */
    public String toString() {
        StringBuilder cb = new StringBuilder(128);
        cb.append(super.toString());
        cb.append("[parent=").append(parent);
        cb.append(", localRefPrefix=").append(localRefPrefix);
        cb.append(", parserPrefixs=");
        String[] prefixs = parserPrefixs;
        if (prefixs != null) {
            for (int i = 0; i < prefixs.length; i++) {
                cb.append(prefixs[i]);
                if (i < prefixs.length) {
                    cb.append(":");
                }
            }
        } else {
            cb.append("null");
        }
        cb.append(", parserSuffix=").append(parserSuffix);
        cb.append(", ignoreCase=").append(ignoreCase);
        cb.append(", localRefPrefix=").append(localRefPrefix);
        cb.append(']');
        return cb.toString();
    }

    /* inner classes */
    class Item {
        int load;
        Element el;
        Object obj;
    }

}