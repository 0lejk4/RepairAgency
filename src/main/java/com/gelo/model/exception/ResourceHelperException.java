package com.gelo.model.exception;

/**
 * The type Resource helper exception.
 * ResourceHelperException is application specific exception.
 */
public class ResourceHelperException extends Exception {
	private String exceptionMessage = "";

    /**
     * Instantiates a new Resource helper exception.
     */
    public ResourceHelperException() {
		super();
	}

    /**
     * Instantiates a new Resource helper exception.
     *
     * @param exceptionMessage the exception message
     */
    public ResourceHelperException(String exceptionMessage) {
		super(exceptionMessage);
		this.exceptionMessage = exceptionMessage;
	}

	@Override
	public String toString() {
		return exceptionMessage;
	}
}
