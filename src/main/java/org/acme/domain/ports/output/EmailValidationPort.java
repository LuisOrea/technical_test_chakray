package org.acme.domain.ports.output;

public interface EmailValidationPort {
    String isValidEmail(String email);
}
