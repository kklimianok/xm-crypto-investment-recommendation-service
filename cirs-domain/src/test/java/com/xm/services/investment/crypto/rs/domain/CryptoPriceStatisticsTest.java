package com.xm.services.investment.crypto.rs.domain;

import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link CryptoPriceStatistics}.
 */
public class CryptoPriceStatisticsTest {

    @Test
    public void testId() {
        CryptoPriceStatistics statistics = new CryptoPriceStatistics();
        statistics.setId(1);
        assertEquals(statistics.getId(), 1);
    }

    @Test
    public void testCrypto() {
        Crypto crypto = new Crypto();
        crypto.setId(2);
        CryptoPriceStatistics statistics = new CryptoPriceStatistics();
        statistics.setCrypto(crypto);
        assertEquals(statistics.getCrypto(), crypto);
    }

    @Test
    public void testStatisticsDate() {
        LocalDate date = LocalDate.now();
        CryptoPriceStatistics statistics = new CryptoPriceStatistics();
        statistics.setStatisticsDate(date);
        assertEquals(statistics.getStatisticsDate(), date);
    }

    @Test
    public void testOldestRecord() {
        CryptoPriceRecord cryptoPriceRecord = new CryptoPriceRecord();
        cryptoPriceRecord.setId(3);
        CryptoPriceStatistics statistics = new CryptoPriceStatistics();
        statistics.setOldestRecord(cryptoPriceRecord);
        assertEquals(statistics.getOldestRecord(), cryptoPriceRecord);
    }

    @Test
    public void testNewestRecord() {
        CryptoPriceRecord cryptoPriceRecord = new CryptoPriceRecord();
        cryptoPriceRecord.setId(4);
        CryptoPriceStatistics statistics = new CryptoPriceStatistics();
        statistics.setNewestRecord(cryptoPriceRecord);
        assertEquals(statistics.getNewestRecord(), cryptoPriceRecord);
    }

    @Test
    public void testMinValueRecord() {
        CryptoPriceRecord cryptoPriceRecord = new CryptoPriceRecord();
        cryptoPriceRecord.setId(5);
        CryptoPriceStatistics statistics = new CryptoPriceStatistics();
        statistics.setMinValueRecord(cryptoPriceRecord);
        assertEquals(statistics.getMinValueRecord(), cryptoPriceRecord);
    }

    @Test
    public void testMaxValueRecord() {
        CryptoPriceRecord cryptoPriceRecord = new CryptoPriceRecord();
        cryptoPriceRecord.setId(6);
        CryptoPriceStatistics statistics = new CryptoPriceStatistics();
        statistics.setMaxValueRecord(cryptoPriceRecord);
        assertEquals(statistics.getMaxValueRecord(), cryptoPriceRecord);
    }
}