package com.gelo.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Nullable.
 * Should be placed on getter of validated field.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Nullable {
    /**
     * Value string.
     * Represents default value for validated field if it is null.
     *
     * @return the string
     */
    String value() default "STATUS_CODE.I_AM_A_TEAPOT";
}
