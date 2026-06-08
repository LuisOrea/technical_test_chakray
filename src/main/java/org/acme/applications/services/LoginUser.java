package org.acme.applications.services;

import org.acme.domain.models.User;
import org.acme.domain.ports.output.EncryptionPort;
import org.acme.domain.ports.output.LoginPort;
import org.acme.domain.ports.output.UserRepositoryPort;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class LoginUser implements LoginPort {
    private final UserRepositoryPort repository;
    private final EncryptionPort encrypted;

    @Override
    public boolean authenticate(String taxId, String password) {
        User user = repository.findByTaxId(taxId);

        if (user == null) {
            System.out.println("LOG: Usuario no encontrado con taxId: " + taxId);
            return false;
        }

        String encryptedInputPassword = encrypted.encrypt(password);


        return encryptedInputPassword.equals(user.getPassword());
    }
}
