package org.acme.infraestructure.output;

import static org.junit.jupiter.api.Assertions.*;
import org.acme.domain.Exceptions.TaxExceptions;
import org.junit.jupiter.api.Test;

public class RfcValidationTest {

    private final RfcValidation validator = new RfcValidation();

    @Test
    public void testIsValidRfc_ValidPerson_ReturnsCleanedRfc() {
        String rfc = "XAXX010101000";
        assertEquals(rfc, validator.isValidRfc("  XAXX-010101-000  "));
    }

    @Test
    public void testIsValidRfc_InvalidLength_ThrowsTaxException() {
        assertThrows(TaxExceptions.class, () -> validator.isValidRfc("ABC1234"));
    }

    @Test
    public void testIsValidRfc_InvalidDate_ThrowsTaxException() {
        assertThrows(TaxExceptions.class, () -> validator.isValidRfc("ABCD901301XYZ"));
    }

    @Test
    public void testIsValidRfc_EmptyOrNull_ThrowsTaxException() {
        assertThrows(TaxExceptions.class, () -> validator.isValidRfc(""));
        assertThrows(TaxExceptions.class, () -> validator.isValidRfc(null));
    }
}