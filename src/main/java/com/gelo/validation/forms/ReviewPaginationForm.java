package com.gelo.validation.forms;

import com.gelo.validation.Nullable;
import com.gelo.validation.Valid;
import com.gelo.validation.Validated;
import com.gelo.validation.constants.Regexps;


/**
 * The type Pagination form.
 */
public class ReviewPaginationForm extends Validated {
    private String pageStr;
    private String countStr;
    private String orderField;
    private String ascendingStr;


    /**
     * Instantiates a new Review pagination form.
     *
     * @param pageStr      the page str
     * @param countStr     the count str
     * @param orderField   the order field
     * @param ascendingStr the ascending str
     */
    public ReviewPaginationForm(String pageStr, String countStr, String orderField, String ascendingStr) {
        this.pageStr = pageStr;
        this.countStr = countStr;
        this.orderField = orderField;
        this.ascendingStr = ascendingStr;
    }

    /**
     * Gets page.
     *
     * @return the page
     */
    public int getPage() {
        return Integer.parseInt(pageStr);
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public int getCount() {
        return Integer.parseInt(countStr);
    }

    /**
     * Gets ascending.
     *
     * @return the ascending
     */
    public boolean getAscending() {
        return Boolean.parseBoolean(ascendingStr);
    }

    /**
     * Gets page str.
     *
     * @return the page str
     */
    @Nullable("1")
    @Valid(value = Regexps.NUM_FROM_ONE, params = {"0", "8"})

    public String getPageStr() {
        return pageStr;
    }

    /**
     * Sets page str.
     *
     * @param pageStr the page str
     */
    public void setPageStr(String pageStr) {
        this.pageStr = pageStr;
    }

    /**
     * Gets count str.
     *
     * @return the count str
     */
    @Valid(value = Regexps.NUM_FROM_ONE, params = {"0", "8"})
    @Nullable("20")
    public String getCountStr() {
        return countStr;
    }

    /**
     * Sets count str.
     *
     * @param countStr the count str
     */
    public void setCountStr(String countStr) {
        this.countStr = countStr;
    }

    /**
     * Gets ascending str.
     *
     * @return the ascending str
     */
    @Valid(value = Regexps.BOOLEAN)
    @Nullable("false")
    public String getAscendingStr() {
        return ascendingStr;
    }

    /**
     * Sets ascending str.
     *
     * @param ascendingStr the ascending str
     */
    public void setAscendingStr(String ascendingStr) {
        this.ascendingStr = ascendingStr;
    }

    /**
     * Gets order field.
     *
     * @return the order field
     */
    @Valid("id|master_id|author_id|title|text|rating")
    @Nullable("id")
    public String getOrderField() {
        return orderField;
    }

    /**
     * Sets order field.
     *
     * @param orderField the order field
     */
    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

}
