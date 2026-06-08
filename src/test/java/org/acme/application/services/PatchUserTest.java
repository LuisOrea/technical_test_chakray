package org.acme.application.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.acme.applications.services.PatchUser;
import org.acme.domain.models.User;
import org.acme.domain.ports.output.*;
import org.junit.jupiter.api.Test;

public class PatchUserTest {

    @Test
    public void testPatch_PartialUpdate_ShouldUpdateOnlyFieldsProvided() {
        // Arrange
        FakeRepository repo = new FakeRepository();
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Original Name");
        existingUser.setEmail("original@test.com");
        repo.userToReturn = existingUser;

        PatchUser service = new PatchUser(repo, s -> s, s -> s, s -> s, s -> s, d -> d);

        User patchData = new User();
        patchData.setName("New Name");
        service.patch(1L, patchData);

        // Assert
        assertEquals("New Name", existingUser.getName());
        assertEquals("original@test.com", existingUser.getEmail()); // No debe cambiar
    }

    @Test
    public void testPatch_UserNotFound_ThrowsException() {
        FakeRepository repo = new FakeRepository();
        repo.userToReturn = null;
        PatchUser service = new PatchUser(repo, null, null, null, null, null);

        assertThrows(IllegalArgumentException.class, () -> service.patch(99L, new User()));
    }

    private static class FakeRepository implements UserRepositoryPort {
        User userToReturn;
        public User findUserId(Long id) { return userToReturn; }
        public User update(User u) { return u; }
        
        // Métodos vacíos obligatorios
        public User save(User u) { return null; }
        public void delete(Long id) {}
        public List<User> getUserFilter(String s1, String s2, String s3, String s4) { return null; }
        public User findByTaxId(String taxId) { return null; }
    }
}