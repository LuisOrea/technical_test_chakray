package org.acme.domain.ports.input;

import org.acme.domain.models.User;

public interface PatchUserPort {
    User patch(Long id, User patchData);
}
