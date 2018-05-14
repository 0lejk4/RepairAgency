package com.gelo.validation;


import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The type Alert.
 */
public class Alert {
    /**
     * The enum Alert type.
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
        INFO;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    private AlertType type;
    private String message;

    /**
     * Danger alert.
     *
     * @param message the message
     * @return the alert
     */
    public static Alert danger(String message) {
        return new Alert(AlertType.DANGER, message);
    }

    /**
     * Success alert.
     *
     * @param message the message
     * @return the alert
     */
    public static Alert success(String message) {
        return new Alert(AlertType.SUCCESS, message);
    }

    /**
     * Info alert.
     *
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

    private Alert(AlertType type, String message) {
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
