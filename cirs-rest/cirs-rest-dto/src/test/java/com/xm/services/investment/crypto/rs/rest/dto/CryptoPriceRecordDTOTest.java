package com.xm.services.investment.crypto.rs.rest.dto;

import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link CryptoPriceRecordDTO}.
 */
public class CryptoPriceRecordDTOTest {

    @Test
    public void testGetId() {
        assertEquals(new CryptoPriceRecordDTO(1, now(), 0.15f).getId(), 1);
    }

    @Test
    public void testGetTimestamp() {
        LocalDateTime timestamp = now();
        assertEquals(new CryptoPriceRecordDTO(1, timestamp, 0.15f).getTimestamp(), timestamp);
    }

    @Test
    public void testGetOpen() {
        assertEquals(new CryptoPriceRecordDTO(1, now(), 0.15f).getPrice(), 0.15f);
    }
}