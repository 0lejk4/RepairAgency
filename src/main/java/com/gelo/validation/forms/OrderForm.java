package com.gelo.validation.forms;

import com.gelo.model.domain.Order;
import com.gelo.validation.Form;
import com.gelo.validation.Valid;
import com.gelo.validation.constants.Regexps;

/**
 * The type Order form.
 * Is used when user creates order.
 */
public class OrderForm extends Form {
    private String description;

    /**
     * Instantiates a new Order form.
     *
     * @param description the description
     */
    public OrderForm(String description) {
        this.description = description;
    }

    /**
     * Parse order order.
     *
     * @return the order
     */
    public Order parseOrder() {
        Order order = new Order();
        order.setAuthorDescription(description);
        return order;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    @Valid(value = Regexps.STR_LEN, params = {"1", "256"})
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
