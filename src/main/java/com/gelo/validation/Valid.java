package com.gelo.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Valid.
 * Should be placed on getter of validated field.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Valid {
    /**
     * Value string.
     * Template for field validation.
     *
     * @return the string
     */
    String value();

    /**
     * Params string [ ].
     * Params for template if it really takes them
     *
     * @return the string [ ]
     */
    String[] params() default "";
}
