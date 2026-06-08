package org.acme.infraestructure.output;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.acme.domain.Exceptions.EmailExceptions;
import org.acme.domain.ports.output.EmailValidationPort;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmailValidation implements EmailValidationPort {

    private static final Pattern pattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");

    @Override
    public String isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new EmailExceptions("Formato de email inválido para el dominio.");
        }

        String cleanEmail = email.trim();

        Matcher matcher = pattern.matcher(cleanEmail);

        if (!matcher.matches()) {
            throw new EmailExceptions("Formato de email inválido.");
        }

        return cleanEmail;
    }
}
