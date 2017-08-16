/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.utils.text;

import java.util.HashSet;

/**
 * Created on 2017/4/18.
 *
 * @author Alan
 * @since 1.0
 */
public class StringNode {
    /**
     * 是否为完整的字符节点(该属性为true时表示可以连接成一个完整字符)
     */
    private boolean isFullNode = false;

    /**
     * 父一个节点
     */
    private StringNode lastNode;

    /**
     * 标记字符
     */
    private String markString;

    /**
     * set列表
     */
    private HashSet<StringNode> set = new HashSet<StringNode>();

    public boolean isFullNode() {
        return isFullNode;
    }

    public void setFullNode(boolean isFullNode) {
        this.isFullNode = isFullNode;
    }

    public String getMarkString() {
        return markString;
    }

    public void setMarkString(String markString) {
        this.markString = markString;
    }

    public HashSet<StringNode> getSet() {
        return set;
    }

    public void setSet(HashSet<StringNode> set) {
        this.set = set;
    }

    public StringNode getLastNode() {
        return lastNode;
    }

    public void setLastNode(StringNode lastNode) {
        this.lastNode = lastNode;
    }

    public void addNode(StringNode node) {
        if (!set.contains(node)) {
            set.add(node);
        }
    }

    /**
     * 向字符节点添加子节点字符
     *
     * @param str
     */
    public void addString(String str) {
        if (str.length() == 0) {
            isFullNode = true;
        } else {
            boolean isFind = false;
            String s = String.valueOf(str.charAt(0));
            for (StringNode node : set) {
                if (node.getMarkString().equals(s)) {
                    node.addString(str.substring(1));
                    isFind = true;
                }
            }

            if (!isFind) {
                StringNode lastNode = this;
                for (int i = 0; i < str.length(); i++) {
                    StringNode node = new StringNode();
                    node.markString = String.valueOf(str.charAt(i));
                    if (i == str.length() - 1) {
                        node.isFullNode = true;
                    }

                    lastNode.addNode(node);
                    node.setLastNode(lastNode);
                    lastNode = node;
                }
            }
        }
    }

    /**
     * 获取匹配字符长度
     *
     * @param str 字符
     * @return
     */
    public int getMatchLenth(String str) {
        if (!str.equals("")) {
            for (StringNode node : set) {
                if (node.markString.equals(String.valueOf(str.charAt(0)))) {
                    int length = node.getMatchLenth(str.substring(1));
                    if (length > 0) {
                        return length;
                    }
                }
            }
        }

        if (isFullNode) {
            int i = 1;
            StringNode laNode = lastNode;
            while (laNode != null) {
                i++;
                laNode = laNode.getLastNode();
            }
            return i;
        }

        return 0;
    }
}
