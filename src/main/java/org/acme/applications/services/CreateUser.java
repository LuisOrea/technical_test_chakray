package org.acme.applications.services;

import java.time.OffsetDateTime;

import org.acme.domain.models.User;
import org.acme.domain.ports.input.CreateUserPort;
import org.acme.domain.ports.output.DateValidationPort;
import org.acme.domain.ports.output.EmailValidationPort;
import org.acme.domain.ports.output.EncryptionPort;
import org.acme.domain.ports.output.PhoneValidationPort;
import org.acme.domain.ports.output.RfcValidationPort;
import org.acme.domain.ports.output.UserRepositoryPort;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class CreateUser implements CreateUserPort {

    EncryptionPort encrypted;
    RfcValidationPort rfcValidation;
    PhoneValidationPort phoneValidation;
    EmailValidationPort emailValidation;
    DateValidationPort dateFormatter;
    UserRepositoryPort repository;


    @Override
    public User create(User user) {
        String passwordEncrypted = this.encrypted.encrypt(user.getPassword());
        user.setPassword(passwordEncrypted);

        String taxValidate = this.rfcValidation.isValidRfc(user.getTaxId());
        user.setTaxId(taxValidate);

        String phoneValidate = this.phoneValidation.isValidPhone(user.getPhone());
        user.setPhone(phoneValidate);

        String emailValidate = this.emailValidation.isValidEmail(user.getEmail());
        user.setEmail(emailValidate);

        OffsetDateTime dateTime = this.dateFormatter.dateFormatter(user.getCreatedAt());
        user.setCreatedAt(dateTime);
        
        return repository.save(user);
    }

}
