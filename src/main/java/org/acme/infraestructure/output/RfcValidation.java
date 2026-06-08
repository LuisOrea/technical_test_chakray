package org.acme.infraestructure.output;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.acme.domain.Exceptions.TaxExceptions;
import org.acme.domain.ports.output.RfcValidationPort;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RfcValidation implements RfcValidationPort {

    private static final Pattern PATTERN = Pattern
            .compile("^([A-ZÑ&]{3,4})([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])([A-Z0-9]{3})$");

    @Override
    public String isValidRfc(String rfc) {
        if (rfc == null || rfc.isBlank()) {
            throw new TaxExceptions("El RFC introducido no cumple con los requisitos necesarios");
        }

        String cleanRfc = rfc.trim().toUpperCase().replaceAll("[\\s-]", "");

        Matcher matcher = PATTERN.matcher(cleanRfc);

        if (!matcher.matches()) {
            throw new TaxExceptions("RFC invalido");
        }
        return cleanRfc;
    }

}
