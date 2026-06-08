package org.acme.applications.services;

import org.acme.domain.ports.input.GetUserPort;
import org.acme.domain.ports.output.UserRepositoryPort;
import org.acme.domain.models.User;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import java.util.List;



@ApplicationScoped
@AllArgsConstructor
public class GetUsers implements GetUserPort {

    private final UserRepositoryPort repository;

    @Override
    public List<User> getUsers(String sortedBy, String filter) {
        String attribute = null;
        String operator = null;
        String value = null;

        if (filter != null && !filter.isBlank()) {

            String[] parts = filter.split("\\+");

            if (parts.length >= 3) {
                attribute = parts[0];
                operator = parts[1];
                value = parts[2];
            } else {
                throw new IllegalArgumentException("El formato del filtro debe ser: campo+operador+valor");
            }
        }

        return repository.getUserFilter(sortedBy, attribute, operator, value);
    }

}
