package org.acme.infraestructure.output;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class DateFormatterTest {

    private final DateFormatter formatter = new DateFormatter();

    @Test
    public void testDateFormatter_NullInput_ReturnsNowInMadagascar() {
        OffsetDateTime result = formatter.dateFormatter(null);

        assertNotNull(result);
        assertEquals(ZoneOffset.of("+03:00"), result.getOffset());
    }

    @Test
    public void testDateFormatter_SpecificDate_ConvertsToMadagascarTime() {
        OffsetDateTime utcDate = OffsetDateTime.of(2026, 6, 8, 12, 0, 0, 0, ZoneOffset.UTC);

        // Act
        OffsetDateTime result = formatter.dateFormatter(utcDate);

        assertEquals(15, result.getHour());
        assertEquals(ZoneOffset.of("+03:00"), result.getOffset());
    }
}