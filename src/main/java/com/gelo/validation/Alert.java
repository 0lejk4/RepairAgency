package com.gelo.validation;


import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The type Alert.
 * Is used as wrapper for errors in order to be shown to user.
 */
public class Alert {
    /**
     * The enum Alert type.
     * Corresponds to all existing bootstrap alert types.
     * Simply call to string on this in jsp like this :
     * 'class="alert alert-${someAlert.toString}"'
     * and you`ll get alert with needed style.
     */
    public enum AlertType {
        /**
         * Danger alert type.
         */
        DANGER, /**
         * Success alert type.
         */
        SUCCESS, /**
         * Info alert type.
         */
        INFO,
        /**
         * Primary alert type.
         */
        PRIMARY,
        /**
         * Secondary alert type.
         */
        SECONDARY,
        /**
         * Warning alert type.
         */
        WARNING,
        /**
         * Light alert type.
         */
        LIGHT,
        /**
         * Dark alert type.
         */
        DARK;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    private AlertType type;
    private String message;

    /**
     * Danger alert.
     * Simpler Constructor call for Danger alerts.
     * @param message the message
     * @return the alert
     */
    public static Alert danger(String message) {
        return new Alert(AlertType.DANGER, message);
    }

    /**
     * Success alert.
     * Simpler Constructor call for Success alerts.
     * @param message the message
     * @return the alert
     */
    public static Alert success(String message) {
        return new Alert(AlertType.SUCCESS, message);
    }

    /**
     * Info alert.
     * Simpler Constructor call for Info alerts.
     * @param message the message
     * @return the alert
     */
    public static Alert info(String message) {
        return new Alert(AlertType.INFO, message);
    }

    /**
     * Single list.
     *
     * @param alert the alert
     * @return the list
     */
    public static List<Alert> single(Alert alert) {
        return Collections.singletonList(alert);
    }

    /**
     * Instantiates a new Alert.
     *
     * @param type    the type
     * @param message the message
     */
    public Alert(AlertType type, String message) {
        this.type = type;
        this.message = message;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public AlertType getType() {
        return type;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alert)) return false;
        Alert alert = (Alert) o;
        return type == alert.type &&
                Objects.equals(message, alert.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, message);
    }
}
