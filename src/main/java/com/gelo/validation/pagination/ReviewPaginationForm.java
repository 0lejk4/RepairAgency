package com.gelo.validation.pagination;

import com.gelo.validation.Nullable;
import com.gelo.validation.PaginationForm;
import com.gelo.validation.Valid;


/**
 * The type Pagination form.
 */
public class ReviewPaginationForm extends PaginationForm {
    private String orderField;

    /**
     * Instantiates a new Review pagination form.
     *
     * @param pageStr      the page str
     * @param countStr     the count str
     * @param orderField   the order field
     * @param ascendingStr the ascending str
     */
    public ReviewPaginationForm(String pageStr, String countStr, String ascendingStr, String orderField) {
        super(pageStr, countStr, ascendingStr);
        this.orderField = orderField;
    }


    /**
     * Gets order field.
     *
     * @return the order field
     */
    @Valid("id|author_id|title|text|rating")
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
