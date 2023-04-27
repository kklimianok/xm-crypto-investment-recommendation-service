package com.xm.services.investment.crypto.rs.domain;

import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link CryptoPriceRecord}.
 */
public class CryptoPriceRecordTest {

    @Test
    public void testId() {
        CryptoPriceRecord cryptoPriceRecord = new CryptoPriceRecord();
        cryptoPriceRecord.setId(5);
        assertEquals(cryptoPriceRecord.getId(), 5);
    }

    @Test
    public void testCrypto() {
        Crypto crypto = new Crypto();
        crypto.setId(8);
        CryptoPriceRecord cryptoPriceRecord = new CryptoPriceRecord();
        cryptoPriceRecord.setCrypto(crypto);
        assertEquals(cryptoPriceRecord.getCrypto(), crypto);
    }

    @Test
    public void testTimestamp() {
        CryptoPriceRecord cryptoPriceRecord = new CryptoPriceRecord();
        LocalDateTime time = LocalDateTime.now();
        cryptoPriceRecord.setTimestamp(time);
        assertEquals(cryptoPriceRecord.getTimestamp(), time);
    }

    @Test
    public void testPrice() {
        CryptoPriceRecord cryptoPriceRecord = new CryptoPriceRecord();
        cryptoPriceRecord.setPrice(1.0f);
        assertEquals(cryptoPriceRecord.getPrice(), 1.0f);
    }
}