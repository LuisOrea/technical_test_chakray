package org.acme.domain.Exceptions;

public class EmailExceptions extends RuntimeException {
    public EmailExceptions(String message) {
        super(message);
    }
}
