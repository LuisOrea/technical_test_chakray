package org.acme.domain.ports.output;

public interface LoginPort {
    boolean authenticate(String taxId, String password);
}
