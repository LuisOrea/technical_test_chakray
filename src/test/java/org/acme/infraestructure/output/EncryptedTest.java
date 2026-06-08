package org.acme.infraestructure.output;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Base64;

public class EncryptedTest {

    @Test
    public void testEncrypt_ShouldReturnValidBase64() {
        // 
        Encrypted enc = new Encrypted();
        enc.init(); 
        
        String input = "password123";
        
        String result = enc.encrypt(input);
        
        assertNotNull(result);
        assertDoesNotThrow(() -> Base64.getDecoder().decode(result), 
            "El resultado debe ser un Base64 válido");
    }

    @Test
    public void testEncrypt_DifferentInputs_ShouldReturnDifferentOutputs() {
        Encrypted enc = new Encrypted();
        enc.init();

        String result1 = enc.encrypt("pass1");
        String result2 = enc.encrypt("pass2");

        assertNotEquals(result1, result2, "Cifrados distintos para entradas distintas");
    }
}