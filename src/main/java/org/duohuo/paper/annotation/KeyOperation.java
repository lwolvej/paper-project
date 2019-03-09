package org.duohuo.paper.annotation;

import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Order(2)
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyOperation {

    enum Operation {
        UPLOAD,
        DELETE
    }

    enum Type {
        JOURNAL,
        PAPER,
        INCITES,
        BASELINE
    }

    Operation operation();

    Type type();
}
