package com.gelo.controller.router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The marker interface Get mapping.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GetMapping {
    /**
     * The constant GET_STR is string representation of RequestMethod.
     */
    String GET_STR = "GET";
}
