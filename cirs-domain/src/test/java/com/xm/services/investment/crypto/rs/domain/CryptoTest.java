package com.xm.services.investment.crypto.rs.domain;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Unit tests for {@link Crypto}.
 */
public class CryptoTest {

    @Test
    public void testId() {
        Crypto crypto = new Crypto();
        crypto.setId(11);
        assertEquals(crypto.getId(), 11);
    }

    @Test
    public void testName() {
        Crypto crypto = new Crypto();
        crypto.setName("Test");
        assertEquals(crypto.getName(), "Test");
    }

    @Test
    public void testStatisticsMonthNumber() {
        Crypto crypto = new Crypto();
        crypto.setStatisticsMonthNumber((byte)6);
        assertEquals(crypto.getStatisticsMonthNumber(), 6);
    }
}