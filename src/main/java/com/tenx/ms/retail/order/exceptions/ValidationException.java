package com.tenx.ms.retail.order.exceptions;

/**
 *
 */
public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 9176663346522902224L;

    public ValidationException(String message) {
        super(message);
    }
}
