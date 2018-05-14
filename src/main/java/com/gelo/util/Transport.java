package com.gelo.util;

/**
 * The type Transport.
 * It is a simple wrapper class that transports
 * data about decision making for MainServlet.
 */
public class Transport {
    /**
     * The enum Transport type.
     */
    public enum TransportType {
        /**
         * Absolute forward transport type.
         */
        ABSOLUTE_FORWARD, /**
         * Relative forward transport type.
         */
        RELATIVE_FORWARD, /**
         * Redirect transport type.
         */
        REDIRECT
    }

    private String url;
    private TransportType type;

    private Transport(String url, TransportType type) {
        this.url = url;
        this.type = type;
    }

    /**
     * Abs forward transport instantiation method.
     *
     * @param url the url
     * @return the transport
     */
    public static Transport absForward(String url) {
        return new Transport(url, TransportType.ABSOLUTE_FORWARD);
    }

    /**
     * Rel forward transport instantiation method.
     *
     * @param url the url
     * @return the transport
     */
    public static Transport relForward(String url) {
        return new Transport(url, TransportType.RELATIVE_FORWARD);
    }

    /**
     * Redirect transport instantiation method.
     *
     * @param url the url
     * @return the transport
     */
    public static Transport redirect(String url) {
        return new Transport(url, TransportType.REDIRECT);
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public TransportType getType() {
        return type;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }
}
