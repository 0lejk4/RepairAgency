package com.gelo.validation.forms;

import com.gelo.model.domain.Review;
import com.gelo.validation.Form;
import com.gelo.validation.Valid;
import com.gelo.validation.constants.Regexps;

/**
 * The type Review form.
 * Is used when user creates review for master.
 */
public class ReviewForm extends Form {
    private String masterId;
    private String title;
    private String text;
    private String rating;

    /**
     * Instantiates a new Review form.
     *
     * @param masterId the master id
     * @param title    the title
     * @param text     the text
     * @param rating   the rating
     */
    public ReviewForm(String masterId, String title, String text, String rating) {
        this.masterId = masterId;
        this.title = title;
        this.text = text;
        this.rating = rating;
    }

    /**
     * Parse review review.
     *
     * @return the review
     */
    public Review parseReview() {
        Review review = new Review(null,
                null,
                title,
                text,
                Review.Star.values()[Integer.parseInt(rating)]);
        review.setMasterId(Long.parseLong(masterId));
        return review;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    @Valid(value = Regexps.STR_LEN, params = {"1", "55"})
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
    @Valid(value = Regexps.STR_LEN, params = {"1", "255"})
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
    @Valid(Regexps.STARS)
    public String getRating() {
        return rating;
    }

    /**
     * Sets rating.
     *
     * @param rating the rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * Gets master id.
     *
     * @return the master id
     */
    @Valid(value = Regexps.NUM_LEN, params = {"1", "9"})
    public String getMasterId() {
        return masterId;
    }

    /**
     * Sets master id.
     *
     * @param masterId the master id
     */
    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }
}
