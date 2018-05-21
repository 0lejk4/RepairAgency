package com.gelo.validation.formats;

import com.gelo.validation.Form;
import com.gelo.validation.Valid;
import com.gelo.validation.constants.Regexps;

/**
 * The type Long format.
 * Simple check if it is Long number or not.
 * Is able to parse long number;
 */
public class LongFormat extends Form {
    private String number;

    /**
     * Instantiates a new Long format.
     *
     * @param number the number
     */
    public LongFormat(String number) {
        this.number = number;
    }

    /**
     * Parse long long.
     *
     * @return the long
     */
    public Long parseLong() {
        return valid() ? Long.parseLong(number) : 0;
    }

    /**
     * Gets number.
     *
     * @return the number
     */
    @Valid(value = Regexps.NUM_LEN, params = {"1", "9"})
    public String getNumber() {
        return number;
    }

    /**
     * Sets number.
     *
     * @param number the number
     */
    public void setNumber(String number) {
        this.number = number;
    }
}
