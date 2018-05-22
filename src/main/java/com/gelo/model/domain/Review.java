package com.gelo.model.domain;

import com.gelo.model.dao.generic.Identified;

import java.io.Serializable;

/**
 * The type Review.
 */
public class Review implements Serializable, Identified<Long> {
    /**
     * The enum Star.
     */
    public enum Star {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE
    }

    private static final long serialVersionUID = 6297385148812380511L;
    private Long id;
    private Long masterId;
    private User author;
    private String title;
    private String text;
    private Star rating;

    /**
     * Instantiates a new Review.
     *
     * @param id     the id
     * @param author the author
     * @param title  the title
     * @param text   the text
     * @param rating the rating
     */
    public Review(Long id, User author, String title, String text, Star rating) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.text = text;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Sets author.
     *
     * @param author the author
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets rating.
     *
     * @return the rating
     */
    public Star getRating() {
        return rating;
    }

    /**
     * Sets rating.
     *
     * @param rating the rating
     */
    public void setRating(Star rating) {
        this.rating = rating;
    }

    /**
     * Gets master id.
     *
     * @return the master id
     */
    public Long getMasterId() {
        return masterId;
    }

    /**
     * Sets master id.
     *
     * @param masterId the master id
     */
    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }


}
