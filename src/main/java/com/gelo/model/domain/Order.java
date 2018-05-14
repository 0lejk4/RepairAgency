package com.gelo.model.domain;

import com.gelo.model.dao.generic.Identified;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Class that represents Order business model
 */
public class Order implements Serializable, Identified<Long> {
    private static final long serialVersionUID = 6297385302182380511L;
    /**
     * Identifier of Order entity
     */
    private Long id;
    /**
     * Author of Order entity
     */
    private User author;
    /**
     * Author`s description of Order entity
     */
    private String authorDescription;
    /**
     * Price of Order entity
     */
    private BigDecimal price;
    /**
     * Master of Order entity
     */
    private User master;
    /**
     * Manager of Order entity
     */
    private User manager;
    /**
     * Managers description of Order entity
     */
    private String managerDescription;
    /**
     * Whenever order entity is accepted by manager
     */
    private boolean accepted;
    /**
     * Whenever order entity is done by master
     */
    private boolean done;

    public Order() {
    }

    public Order(Long id, User author, String authorDescription, BigDecimal price, User master, User manager, String managerDescription, boolean accepted, boolean done) {
        this.id = id;
        this.author = author;
        this.authorDescription = authorDescription;
        this.price = price;
        this.master = master;
        this.manager = manager;
        this.managerDescription = managerDescription;
        this.accepted = accepted;
        this.done = done;
    }

    /**
     * {@link Order#id}
     */
    public Long getId() {
        return id;
    }

    /**
     * {@link Order#id}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * {@link Order#author}
     */
    public User getAuthor() {
        return author;
    }

    /**
     * {@link Order#author}
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * {@link Order#authorDescription}
     */
    public String getAuthorDescription() {
        return authorDescription;
    }

    /**
     * {@link Order#authorDescription}
     */
    public void setAuthorDescription(String authorDescription) {
        this.authorDescription = authorDescription;
    }
    /**
     * {@link Order#price}
     */
    public BigDecimal getPrice() {
        return price;
    }
    /**
     * {@link Order#price}
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    /**
     * {@link Order#master}
     */
    public User getMaster() {
        return master;
    }
    /**
     * {@link Order#master}
     */
    public void setMaster(User master) {
        this.master = master;
    }
    /**
     * {@link Order#manager}
     */
    public User getManager() {
        return manager;
    }
    /**
     * {@link Order#manager}
     */
    public void setManager(User manager) {
        this.manager = manager;
    }
    /**
     * {@link Order#accepted}
     */
    public boolean isAccepted() {
        return accepted;
    }
    /**
     * {@link Order#accepted}
     */
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
    /**
     * {@link Order#managerDescription}
     */
    public String getManagerDescription() {
        return managerDescription;
    }
    /**
     * {@link Order#managerDescription}
     */
    public void setManagerDescription(String managerDescription) {
        this.managerDescription = managerDescription;
    }
    /**
     * {@link Order#done}
     */
    public boolean isDone() {
        return done;
    }
    /**
     * {@link Order#done}
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", author=" + (author == null ? "null" : author.getName()) +
                ", price=" + price +
                ", master=" + (master == null ? "null" : master.getName()) +
                ", manager=" + (manager == null ? "null" : manager.getName()) +
                ", accepted=" + accepted +
                ", done=" + done +
                '}';
    }
}
