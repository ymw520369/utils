/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on 2017/4/18.
 *
 * @author Alan
 * @since 1.0
 */
public class AtomicUtil {
    /**
     * 为指定的原子整型对象增加一个值
     * <p>1、如果被增加的值是一个小于等于0的值，则直接返回true
     * <p>2、如果增加后的值超出整型的上线则返回失败
     *
     * @param atomicInteger
     * @param addValue
     * @return
     */
    public static boolean increase(AtomicInteger atomicInteger, int addValue) {
        if (addValue <= 0) {
            return true;
        }
        long pev, next;
        boolean result;
        do {
            pev = atomicInteger.longValue();
            next = pev + addValue;
            result = next <= Integer.MAX_VALUE;
        } while (result && !atomicInteger.compareAndSet((int) pev, (int) next));
        return result;
    }

    /**
     * 为指定的原子整型对象减少一个值
     * <p>1、如果被减数是一个小于等于0的值，则直接返回true
     * <p>2、如果被减后的值小于0，则返回false
     *
     * @param atomicInteger
     * @param decreaseValue
     * @return
     */
    public static boolean decrease(AtomicInteger atomicInteger, int decreaseValue) {
        if (decreaseValue <= 0) {
            return true;
        }
        long pev, next;
        boolean result;
        do {
            pev = atomicInteger.longValue();
            next = pev - decreaseValue;
            result = next >= 0;
        } while (result && !atomicInteger.compareAndSet((int) pev, (int) next));
        return result;
    }
}
