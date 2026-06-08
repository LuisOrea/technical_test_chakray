package org.acme.infraestructure.output;

import java.util.regex.Pattern;

import org.acme.domain.Exceptions.PhoneExceptions;
import org.acme.domain.ports.output.PhoneValidationPort;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PhoneValidation implements PhoneValidationPort {

    private static final Pattern pattern = Pattern.compile("^(\\+\\d{1,3})?\\d{10}$");

    @Override
    public String isValidPhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new PhoneExceptions("Se requiere ingresar el número.");
        }

        String cleanPhone = phone.replaceAll("[\\s\\-\\(\\)\\.]", "");

        if (!pattern.matcher(cleanPhone).matches()) {
            throw new PhoneExceptions("Formato de número inválido. Debe contener 10 dígitos.");
        }

        String tenDigits = cleanPhone.substring(cleanPhone.length() - 10);

        if (tenDigits.startsWith("0")) {
            throw new PhoneExceptions("El número no cumple con el formato AndresFormat (no puede comenzar con 0).");
        }

        return cleanPhone;
    }
}
