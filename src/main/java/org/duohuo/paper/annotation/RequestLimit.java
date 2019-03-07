package org.duohuo.paper.annotation;

import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Order(1)
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLimit {

    int count() default Integer.MAX_VALUE;

    long time() default 60000;
}
