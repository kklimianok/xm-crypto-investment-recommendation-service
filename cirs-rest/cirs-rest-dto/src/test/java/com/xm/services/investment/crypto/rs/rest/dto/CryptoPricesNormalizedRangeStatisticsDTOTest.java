package com.xm.services.investment.crypto.rs.rest.dto;

import org.testng.annotations.Test;

import java.time.LocalDate;

import static java.time.LocalDate.now;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link CryptoPricesNormalizedRangeStatisticsDTO}.
 */
public class CryptoPricesNormalizedRangeStatisticsDTOTest {

    @Test
    public void testGetDate() {
        LocalDate date = now();
        CryptoPricesNormalizedRangeStatisticsDTO dto = new CryptoPricesNormalizedRangeStatisticsDTO(date, emptySet());
        assertEquals(dto.getStatisticsDate(), date);
    }

    @Test
    public void testGetNormalizedRanges() {
        CryptoPricesNormalizedRangeDTO rangeDTO = new CryptoPricesNormalizedRangeDTO(1, "t", 0.2f);
        CryptoPricesNormalizedRangeStatisticsDTO dto = new CryptoPricesNormalizedRangeStatisticsDTO(null,
            singleton(rangeDTO));
        assertEquals(dto.getNormalizedRanges().size(), 1);
        assertEquals(dto.getNormalizedRanges().iterator().next(), rangeDTO);
    }
}