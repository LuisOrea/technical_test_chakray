package org.acme.applications.services;

import org.acme.domain.models.User;
import org.acme.domain.ports.input.DeleteUserPort;
import org.acme.domain.ports.output.UserRepositoryPort;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class DeleteUser implements DeleteUserPort {

    UserRepositoryPort repository;

    @Override
    public void delete(Long id) {

        User currentUser = repository.findUserId(id);

        if (currentUser == null) {
            throw new IllegalArgumentException("User does not exist");
        }

        repository.delete(currentUser.getId());
    }
}
