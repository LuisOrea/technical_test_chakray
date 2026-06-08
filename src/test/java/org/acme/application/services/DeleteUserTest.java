package org.acme.application.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.acme.applications.services.DeleteUser;
import org.acme.domain.models.User;
import org.acme.domain.ports.output.UserRepositoryPort;
import org.junit.jupiter.api.Test;

public class DeleteUserTest {

    @Test
    public void testDelete_ShouldDeleteUser_WhenUserExists() {

        FakeRepository repo = new FakeRepository();
        repo.userToReturn = new User();
        repo.userToReturn.setId(10L);

        DeleteUser service = new DeleteUser(repo);

        service.delete(10L);

        assertTrue(repo.deleteCalled);
    }

    @Test
    public void testDelete_ShouldThrowException_WhenUserDoesNotExist() {

        FakeRepository repo = new FakeRepository();
        repo.userToReturn = null;

        DeleteUser service = new DeleteUser(repo);

        assertThrows(IllegalArgumentException.class, () -> {
            service.delete(99L);
        });
    }

    
    private static class FakeRepository implements UserRepositoryPort {
        User userToReturn;
        boolean deleteCalled = false;

        public User findUserId(Long id) {
            return userToReturn;
        }

        public void delete(Long id) {
            deleteCalled = true;
        }

        public User save(User u) {
            return null;
        }

        public User update(User u) {
            return u;
        }

        public List<User> getUserFilter(String s1, String s2, String s3, String s4) {
            return null;
        }

        public User findByTaxId(String taxId) {
            return null;
        }
    }
}