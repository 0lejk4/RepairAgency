package com.gelo.validation;

import com.gelo.validation.constants.Regexps;

public abstract class PaginationForm extends Form {
    private String pageStr;
    private String countStr;
    private String ascendingStr;

    public PaginationForm(String pageStr, String countStr, String ascendingStr) {
        this.pageStr = pageStr;
        this.countStr = countStr;
        this.ascendingStr = ascendingStr;
    }

    public abstract String getOrderField();

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
}
