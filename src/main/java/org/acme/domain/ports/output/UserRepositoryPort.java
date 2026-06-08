package org.acme.domain.ports.output;

import java.util.List;
import org.acme.domain.models.User;

public interface UserRepositoryPort {
    User save(User user);

    User findUserId(Long id);

    List<User> getUserFilter(String sortedBy, String attribute, String operator, String value);

    User findByTaxId(String taxId);

    void delete(Long id);

    User update(User user);
}
