package org.acme.domain.ports.output;

import java.time.OffsetDateTime;

public interface DateValidationPort {
    OffsetDateTime dateFormatter(OffsetDateTime date);
}
