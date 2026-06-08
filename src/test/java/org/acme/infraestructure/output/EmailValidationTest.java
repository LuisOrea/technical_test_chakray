package org.acme.infraestructure.output;

import static org.junit.jupiter.api.Assertions.*;
import org.acme.domain.Exceptions.EmailExceptions;
import org.junit.jupiter.api.Test;

public class EmailValidationTest {

    private final EmailValidation validator = new EmailValidation();

    @Test
    public void testIsValidEmail_ValidFormats_ReturnsEmail() {
        assertEquals("test@example.com", validator.isValidEmail("test@example.com"));
        assertEquals("user.name@domain.co", validator.isValidEmail("user.name@domain.co"));
        assertEquals("user_123@sub.domain.org", validator.isValidEmail("user_123@sub.domain.org"));
    }

    @Test
    public void testIsValidEmail_InvalidFormats_ThrowsEmailException() {
        assertThrows(EmailExceptions.class, () -> validator.isValidEmail("testexample.com"));
        assertThrows(EmailExceptions.class, () -> validator.isValidEmail("test@domain"));
        assertThrows(EmailExceptions.class, () -> validator.isValidEmail("test user@domain.com"));
    }

    @Test
    public void testIsValidEmail_EmptyOrNull_ThrowsEmailException() {
        assertThrows(EmailExceptions.class, () -> validator.isValidEmail(null));
        assertThrows(EmailExceptions.class, () -> validator.isValidEmail("   "));
    }
}