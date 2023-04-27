package com.xm.services.investment.crypto.rs.rest.dto;

import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.LocalDate.now;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link CryptoPricesStatisticsDTO}.
 */
public class CryptoPricesStatisticsDTOTest {

    @Test
    public void testGetStatisticsDate() {
        LocalDate date = now();
        assertEquals(CryptoPricesStatisticsDTO.builder().statisticsDate(date).build().getStatisticsDate(), date);
    }

    @Test
    public void testGetOldest() {
        CryptoPriceRecordDTO dto = new CryptoPriceRecordDTO(1, LocalDateTime.now(), 0.3f);
        assertEquals(CryptoPricesStatisticsDTO.builder().oldest(dto).build().getOldest(), dto);
    }

    @Test
    public void testGetNewest() {
        CryptoPriceRecordDTO dto = new CryptoPriceRecordDTO(2, LocalDateTime.now(), 0.5f);
        assertEquals(CryptoPricesStatisticsDTO.builder().newest(dto).build().getNewest(), dto);
    }

    @Test
    public void testGetMin() {
        CryptoPriceRecordDTO dto = new CryptoPriceRecordDTO(3, LocalDateTime.now(), 0.7f);
        assertEquals(CryptoPricesStatisticsDTO.builder().min(dto).build().getMin(), dto);
    }

    @Test
    public void testGetMax() {
        CryptoPriceRecordDTO dto = new CryptoPriceRecordDTO(4, LocalDateTime.now(), 0.9f);
        assertEquals(CryptoPricesStatisticsDTO.builder().max(dto).build().getMax(), dto);
    }
}