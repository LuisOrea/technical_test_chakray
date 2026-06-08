package org.acme.applications.services;

import java.time.OffsetDateTime;

import org.acme.domain.models.User;
import org.acme.domain.ports.input.PatchUserPort;
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
public class PatchUser implements PatchUserPort {

    UserRepositoryPort repository;
    EncryptionPort encrypted;
    RfcValidationPort rfcValidation;
    PhoneValidationPort phoneValidation;
    EmailValidationPort emailValidation;
    DateValidationPort dateFormatter;

    @Override
    public User patch(Long id, User patchData) {
        User currentUser = repository.findUserId(id);

        if (currentUser == null) {
            throw new IllegalArgumentException("User not found");
        }

        OffsetDateTime originalDate = currentUser.getCreatedAt();

        if (patchData.getEmail() != null && !patchData.getEmail().isBlank()) {
            String newEmail = emailValidation.isValidEmail(patchData.getEmail());
            currentUser.setEmail(newEmail);
        }

        if (patchData.getName() != null && !patchData.getName().isBlank()) {
            String newName = patchData.getName();
            currentUser.setName(newName);
        }

        if (patchData.getPhone() != null && !patchData.getPhone().isBlank()) {
            String newPhone = phoneValidation.isValidPhone(patchData.getPhone());
            currentUser.setPhone(newPhone);
        }

        if (patchData.getPassword() != null && !patchData.getPassword().isBlank()) {
            String newPassword = encrypted.encrypt(patchData.getPassword());
            currentUser.setPassword(newPassword);
        }

        if (patchData.getTaxId() != null && !patchData.getTaxId().isBlank()) {
            String newTaxId = rfcValidation.isValidRfc(patchData.getTaxId());
            currentUser.setTaxId(newTaxId);
        }

        if (patchData.getAddresses() != null) {

            patchData.getAddresses().forEach(patchAdd -> {

                if (patchAdd.getId() == null) {
                    throw new IllegalArgumentException("Id is required");
                }

                currentUser.getAddresses().stream().filter(existing -> existing.getId().equals(patchAdd.getId()))
                        .findFirst().ifPresent(existingAdd -> {

                            if (patchAdd.getName() != null && !patchAdd.getName().isBlank()) {
                                existingAdd.setName(patchAdd.getName());
                            }

                            if (patchAdd.getStreet() != null && !patchAdd.getStreet().isBlank()) {
                                existingAdd.setStreet(patchAdd.getStreet());
                            }

                            if (patchAdd.getCountryCode() != null && !patchAdd.getCountryCode().isBlank()) {
                                existingAdd.setCountryCode(patchAdd.getCountryCode());
                            }
                        });
            });

            currentUser.setAddresses(currentUser.getAddresses());
        }

        currentUser.setCreatedAt(originalDate);

        return repository.update(currentUser);
    }
}
