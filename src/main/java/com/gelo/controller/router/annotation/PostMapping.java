package com.gelo.controller.router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The marker interface Post mapping.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PostMapping {
    /**
     * The constant POST_STR is string representation of RequestMethod.
     */
    String POST_STR = "POST";
}
