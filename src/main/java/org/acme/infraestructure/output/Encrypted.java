package org.acme.infraestructure.output;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.acme.domain.ports.output.EncryptionPort;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@ApplicationScoped
public class Encrypted implements EncryptionPort {

    private static final String ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BITS = 128;
    private static final int IV_LENGTH_BYTE = 12;
    private static final String FIXED_KEY = "12345678901234567890123456789012";
    private static final byte[] FIXED_IV = new byte[IV_LENGTH_BYTE]; 
    private SecretKey llaveSecreta;

    @PostConstruct
    void init() {
        this.llaveSecreta = new SecretKeySpec(FIXED_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
    }

    @Override
    public String encrypt(String textoPlano) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(TAG_LENGTH_BITS, FIXED_IV);
            cipher.init(Cipher.ENCRYPT_MODE, llaveSecreta, gcmParameterSpec);

            byte[] textoCifrado = cipher.doFinal(textoPlano.getBytes(StandardCharsets.UTF_8));

            ByteBuffer byteBuffer = ByteBuffer.allocate(FIXED_IV.length + textoCifrado.length);
            byteBuffer.put(FIXED_IV);
            byteBuffer.put(textoCifrado);

            return Base64.getEncoder().encodeToString(byteBuffer.array());
        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar", e);
        }
    }

}