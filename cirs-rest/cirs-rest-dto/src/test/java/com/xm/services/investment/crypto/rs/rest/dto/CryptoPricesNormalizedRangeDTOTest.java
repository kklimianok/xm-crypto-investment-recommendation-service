package com.xm.services.investment.crypto.rs.rest.dto;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link CryptoPricesNormalizedRangeDTO}.
 */
public class CryptoPricesNormalizedRangeDTOTest {

    @Test
    public void testGetId() {
        CryptoPricesNormalizedRangeDTO dto = new CryptoPricesNormalizedRangeDTO(1, "test1", 0.3f);
        assertEquals(dto.getId(), 1);
    }

    @Test
    public void testGetName() {
        CryptoPricesNormalizedRangeDTO dto = new CryptoPricesNormalizedRangeDTO(2, "test2", 0.2f);
        assertEquals(dto.getName(), "test2");
    }

    @Test
    public void testGetNormalizedRange() {
        CryptoPricesNormalizedRangeDTO dto = new CryptoPricesNormalizedRangeDTO(3, "test3", 0.25f);
        assertEquals(dto.getNormalizedRange(), 0.25);
    }
}