package org.acme.application.services;

import static org.junit.jupiter.api.Assertions.*;

import org.acme.applications.services.CreateUser;
import org.acme.domain.models.User;
import org.acme.domain.ports.output.*;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import java.util.List;

public class CreateUserTest {

    @Test
    public void testCreate_ShouldProcessUserCorrectly() {

        CreateUser service = new CreateUser(
                new FakeEncryption(),
                new FakeRfc(),
                new FakePhone(),
                new FakeEmail(),
                new FakeDate(),
                new FakeRepository());

        User user = new User();
        user.setPassword("12345");
        user.setTaxId("RFC1");
        user.setPhone("123");
        user.setEmail("test@test.com");
        user.setCreatedAt(OffsetDateTime.now());

        User result = service.create(user);

        assertEquals("ENCRYPTED_12345", result.getPassword());
        assertEquals("VALID_RFC1", result.getTaxId());
    }

    private static class FakeEncryption implements EncryptionPort {
        public String encrypt(String s) {
            return "ENCRYPTED_" + s;
        }
    }

    private static class FakeRfc implements RfcValidationPort {
        public String isValidRfc(String s) {
            return "VALID_" + s;
        }
    }

    private static class FakePhone implements PhoneValidationPort {
        public String isValidPhone(String s) {
            return "VALID_" + s;
        }
    }

    private static class FakeEmail implements EmailValidationPort {
        public String isValidEmail(String s) {
            return "VALID_" + s;
        }
    }

    private static class FakeDate implements DateValidationPort {
        public OffsetDateTime dateFormatter(OffsetDateTime d) {
            return d;
        }
    }

    private static class FakeRepository implements UserRepositoryPort {
        @Override
        public User save(User u) {
            return u;
        }

        @Override
        public void delete(Long id) {
        }

        @Override
        public User update(User u) {
            return u;
        }

        @Override
        public List<User> getUserFilter(String s1, String s2, String s3, String s4) {
            return null;
        }

        @Override
        public User findByTaxId(String taxId) {
            return null;
        }

        @Override
        public User findUserId(Long id) {
            return null;
        }
    }
}