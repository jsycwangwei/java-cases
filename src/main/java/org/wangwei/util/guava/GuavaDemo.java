package org.wangwei.util.guava;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class GuavaDemo {
    public static void main(String[] args) {
        Function<Integer, Integer> square = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer item) {
                return item * item;
            }
        };
        Collections2.transform(Lists.newArrayList(1, 2, 3), square);

        Predicate<Integer> isOdd = new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return (input & 1) != 0;
            }
        };
    }
}
