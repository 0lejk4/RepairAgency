package com.gelo.validation.forms;

import com.gelo.model.domain.Order;
import com.gelo.validation.Form;
import com.gelo.validation.Valid;
import com.gelo.validation.constants.Regexps;

import java.math.BigDecimal;

/**
 * The type Answer order form.
 * Is used when validating manager answer.
 */
public class AnswerOrderForm extends Form {
    private String id;
    private String description;
    private String price;

    /**
     * Instantiates a new Answer order form.
     *
     * @param id          the id
     * @param description the description
     * @param price       the price
     */
    public AnswerOrderForm(String id, String description, String price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    /**
     * Parse order order.
     *
     * @return the order
     */
    public Order parseOrder() {
        Order order = new Order();
        order.setId(Long.parseLong(id));
        order.setManagerDescription(description);
        order.setPrice(BigDecimal.valueOf(Long.parseLong(price)));
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

    /**
     * Gets id.
     *
     * @return the id
     */
    @Valid(value = Regexps.NUM_LEN, params = {"1", "9"})
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    @Valid(value = Regexps.NUM_LEN, params = {"1", "9"})
    public String getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(String price) {
        this.price = price;
    }
}
