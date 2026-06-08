package org.acme.infraestructure.output;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.acme.domain.ports.output.DateValidationPort;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DateFormatter implements DateValidationPort {

    private static final ZoneId MADAGASCAR_ZONE = ZoneId.of("Indian/Antananarivo");

    @Override
    public OffsetDateTime dateFormatter(OffsetDateTime date) {

        if (date == null) {
            return OffsetDateTime.now(MADAGASCAR_ZONE);
        }

        return date.atZoneSameInstant(MADAGASCAR_ZONE).toOffsetDateTime();
    }
}
