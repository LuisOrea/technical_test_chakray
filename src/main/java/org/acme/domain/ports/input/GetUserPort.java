package org.acme.domain.ports.input;

import java.util.List;

import org.acme.domain.models.User;

public interface GetUserPort {
    List<User> getUsers(String sortedBy, String filter);
}
