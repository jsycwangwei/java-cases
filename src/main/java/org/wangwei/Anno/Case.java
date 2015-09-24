/*
 * Copyright 2015 Focus Technology, Co., Ltd. All rights reserved.
 */
package org.wangwei.Anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Case.java
 *
 * @author wangwei-ww
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Case {
}
