package org.acme.infraestructure.output;

import static org.junit.jupiter.api.Assertions.*;
import org.acme.domain.Exceptions.PhoneExceptions;
import org.junit.jupiter.api.Test;

public class PhoneValidationTest {

    private final PhoneValidation validator = new PhoneValidation();

    @Test
    public void testIsValidPhone_ValidFormat_ReturnsCleanedPhone() {
        assertEquals("5512345678", validator.isValidPhone("55-1234-5678"));
        assertEquals("+525512345678", validator.isValidPhone("+52(55)1234.5678"));
    }

    @Test
    public void testIsValidPhone_StartingWithZero_ThrowsPhoneException() {
        String input = "0501234567"; 
        
        String clean = input.replaceAll("[\\s\\-\\(\\)\\.]", "");
        String tenDigits = clean.substring(clean.length() - 10);
        
        System.out.println("DEBUG: clean=" + clean);
        System.out.println("DEBUG: tenDigits=" + tenDigits);
        System.out.println("DEBUG: empieza con 0? " + tenDigits.startsWith("0"));

        assertThrows(PhoneExceptions.class, () -> validator.isValidPhone(input), 
            "Esperábamos excepción porque el número empieza con 0");
    }

    @Test
    public void testIsValidPhone_InvalidLength_ThrowsPhoneException() {
        assertThrows(PhoneExceptions.class, () -> validator.isValidPhone("12345"));
    }

    @Test
    public void testIsValidPhone_EmptyOrNull_ThrowsPhoneException() {
        assertThrows(PhoneExceptions.class, () -> validator.isValidPhone(null));
        assertThrows(PhoneExceptions.class, () -> validator.isValidPhone("   "));
    }
}