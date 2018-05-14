package com.gelo.validation.constants;

/**
 * The interface Regexps.
 */
public interface Regexps {
    /**
     * The constant EMAIL.
     */
    String EMAIL = "[a-z0-9._%-]+@[a-z0-9._%-]+\\.[a-z]{2,4}";
    /**
     * The constant STR_LEN. This template uses 2 parameters to limit string of length in that range
     */
    String STR_LEN = "^.{%s,%s}$";
    /**
     * The constant NUM_LEN. This template uses 2 parameters to limit numbers of length in that range
     */
    String NUM_LEN = "^\\d{%s,%s}$";
    /**
     * The constant NUM_FROM_ONE. This template uses 2 parameters to limit numbers of length in that range
     */
    String NUM_FROM_ONE = "^[1-9][0-9]{%s,%s}$";
    /**
     * The constant STARS.
     */
    String STARS = "^[0-4]$";
    /**
     * The constant ROLES.
     */
    String ROLES = "^[1-4]$";
    /**
     * The constant BOOLEAN.
     */
    String BOOLEAN = "^true|false$";
}
