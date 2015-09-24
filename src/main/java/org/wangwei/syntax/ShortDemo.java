/*
 * Copyright 2015 Focus Technology, Co., Ltd. All rights reserved.
 */
package org.wangwei.syntax;

import org.junit.Test;

/**
 * Short为引用传值<br>
 *
 * @author wangwei-ww
 */
public class ShortDemo {
    @Test
    public void test() {
        Short s = new Short((short) 1);
        Short s1 = 1;
        System.out.println(s1 == s);
        System.out.println(s1.equals(s));
    }

    @Test
    public void test1() {
        Short s = 2;
        Short s1 = 1;
        s--;
        System.out.println(s1 == s);
        System.out.println(s1.equals(s));
    }

    @Test
    public void test2() {
        Short s = 128;
        Short s1 = 127;
        s--;
        System.out.println(s1 == s);
        System.out.println(s1.equals(s));
    }

    @Test
    public void test3() {
        Short s = 129;
        Short s1 = 128;
        s--;
        System.out.println(s1 == s);
        System.out.println(s1.equals(s));
    }
}
