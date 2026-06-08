package org.acme.application.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.acme.applications.services.LoginUser;
import org.acme.domain.models.User;
import org.acme.domain.ports.output.EncryptionPort;
import org.acme.domain.ports.output.UserRepositoryPort;
import org.junit.jupiter.api.Test;

public class LoginUserTest {

    @Test
    public void testAuthenticate_Success() {
        FakeRepository repo = new FakeRepository();
        User user = new User();
        user.setPassword("ENCRYPTED_123");
        repo.userToReturn = user;

        FakeEncryption enc = new FakeEncryption();
        LoginUser service = new LoginUser(repo, enc);

        boolean result = service.authenticate("RFC123", "123");
        
        assertTrue(result, "La autenticación debería ser exitosa");
    }

    @Test
    public void testAuthenticate_UserNotFound_ReturnsFalse() {
        FakeRepository repo = new FakeRepository();
        repo.userToReturn = null; 

        LoginUser service = new LoginUser(repo, new FakeEncryption());

        boolean result = service.authenticate("UNKNOWN", "123");
        
        assertFalse(result, "Si el usuario no existe, debe retornar false");
    }

    @Test
    public void testAuthenticate_WrongPassword_ReturnsFalse() {
        FakeRepository repo = new FakeRepository();
        User user = new User();
        user.setPassword("ENCRYPTED_CORRECT");
        repo.userToReturn = user;

        FakeEncryption enc = new FakeEncryption();
        LoginUser service = new LoginUser(repo, enc);

        boolean result = service.authenticate("RFC123", "WRONG_PASS");
        
        assertFalse(result, "La contraseña incorrecta debe retornar false");
    }

  
    private static class FakeRepository implements UserRepositoryPort {
        User userToReturn;
        public User findByTaxId(String taxId) { return userToReturn; }
        
        // Métodos vacíos obligatorios por la interfaz
        public User save(User u) { return null; }
        public void delete(Long id) {}
        public User update(User u) { return null; }
        public List<User> getUserFilter(String s1, String s2, String s3, String s4) { return null; }
        public User findUserId(Long id) { return null; }
    }

    private static class FakeEncryption implements EncryptionPort {
        public String encrypt(String raw) { return "ENCRYPTED_" + raw; }
    }
}