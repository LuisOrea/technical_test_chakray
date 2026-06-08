package org.acme.domain.ports.input;


import org.acme.domain.models.User;

public interface CreateUserPort {
    User create(User user);

}
