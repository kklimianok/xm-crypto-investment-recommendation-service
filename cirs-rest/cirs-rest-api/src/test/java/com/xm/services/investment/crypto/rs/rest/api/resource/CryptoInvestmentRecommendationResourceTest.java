package com.xm.services.investment.crypto.rs.rest.api.resource;

import com.xm.services.investment.crypto.rs.domain.Crypto;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceRecord;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceStatistics;
import com.xm.services.investment.crypto.rs.rest.dto.CryptoPriceRecordDTO;
import com.xm.services.investment.crypto.rs.rest.dto.CryptoPricesNormalizedRangeDTO;
import com.xm.services.investment.crypto.rs.rest.dto.CryptoPricesNormalizedRangeStatisticsDTO;
import com.xm.services.investment.crypto.rs.rest.dto.CryptoPricesStatisticsDTO;
import com.xm.services.investment.crypto.rs.rest.service.CryptoInvestmentRecommendationService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static java.time.LocalDate.now;
import static java.time.LocalDate.parse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.OK;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Unit tests for {@link CryptoInvestmentRecommendationResource}.
 */
public class CryptoInvestmentRecommendationResourceTest {

    @InjectMocks
    private CryptoInvestmentRecommendationResource resource;

    @Mock
    private CryptoInvestmentRecommendationService service;

    @BeforeMethod
    public void setUp() {
        openMocks(this);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetCryptoNormalizedRanges() {
        Crypto crypto = mock(Crypto.class);
        when(crypto.getId()).thenReturn(1L);
        when(crypto.getName()).thenReturn("test");
        Map<LocalDate, Set<SimpleEntry<Crypto, Float>>> ranges = newHashMap();
        ranges.put(now(), newHashSet(new SimpleEntry<>(crypto, 0.2f)));
        when(service.getNormalizedRangePrices(null)).thenReturn(ranges);

        ResponseEntity<Collection<CryptoPricesNormalizedRangeStatisticsDTO>> result
            = resource.getCryptoNormalizedRanges(null);

        assertEquals(result.getStatusCode(), OK);
        Collection<CryptoPricesNormalizedRangeStatisticsDTO> dtos = result.getBody();
        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        CryptoPricesNormalizedRangeStatisticsDTO statsDTO = dtos.iterator().next();
        assertEquals(statsDTO.getStatisticsDate(), now());
        assertEquals(statsDTO.getNormalizedRanges().size(), 1);
        CryptoPricesNormalizedRangeDTO dto = statsDTO.getNormalizedRanges().iterator().next();
        assertEquals(dto.getId(), 1L);
        assertEquals(dto.getName(), "test");
        assertEquals(dto.getNormalizedRange(), 0.2f);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetCryptoNormalizedRangesWithSpecifiedDate() {
        Crypto crypto = mock(Crypto.class);
        when(crypto.getId()).thenReturn(1L);
        when(crypto.getName()).thenReturn("test");
        Map<LocalDate, Set<SimpleEntry<Crypto, Float>>> ranges = newHashMap();
        ranges.put(parse("2023-04-26"), newHashSet(new SimpleEntry<>(crypto, 0.2f)));
        when(service.getNormalizedRangePrices(parse("2023-04-26"))).thenReturn(ranges);

        ResponseEntity<Collection<CryptoPricesNormalizedRangeStatisticsDTO>> result
            = resource.getCryptoNormalizedRanges("2023-04-26");

        assertEquals(result.getStatusCode(), OK);
        Collection<CryptoPricesNormalizedRangeStatisticsDTO> dtos = result.getBody();
        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        CryptoPricesNormalizedRangeStatisticsDTO statsDTO = dtos.iterator().next();
        assertEquals(statsDTO.getStatisticsDate(), parse("2023-04-26"));
        assertEquals(statsDTO.getNormalizedRanges().size(), 1);
        CryptoPricesNormalizedRangeDTO dto = statsDTO.getNormalizedRanges().iterator().next();
        assertEquals(dto.getId(), 1L);
        assertEquals(dto.getName(), "test");
        assertEquals(dto.getNormalizedRange(), 0.2f);
    }

    @Test
    public void testGetAllStatisticsValues() {
        CryptoPriceStatistics statistics = mock(CryptoPriceStatistics.class);
        CryptoPriceRecord oldest = mock(CryptoPriceRecord.class);
        when(oldest.getId()).thenReturn(5L);
        CryptoPriceRecord newest = mock(CryptoPriceRecord.class);
        when(newest.getId()).thenReturn(15L);
        CryptoPriceRecord min = mock(CryptoPriceRecord.class);
        when(min.getId()).thenReturn(25L);
        CryptoPriceRecord max = mock(CryptoPriceRecord.class);
        when(max.getId()).thenReturn(35L);
        when(statistics.getOldestRecord()).thenReturn(oldest);
        when(statistics.getNewestRecord()).thenReturn(newest);
        when(statistics.getMinValueRecord()).thenReturn(min);
        when(statistics.getMaxValueRecord()).thenReturn(max);
        Map<LocalDate, CryptoPriceStatistics> dateStatisticsMap = newHashMap();
        dateStatisticsMap.put(now(), statistics);
        when(service.getPricesStatistics("test")).thenReturn(dateStatisticsMap);

        ResponseEntity<Collection<CryptoPricesStatisticsDTO>> result = resource.getAllStatisticsValues("test");

        assertEquals(result.getStatusCode(), OK);
        Collection<CryptoPricesStatisticsDTO> dtos = result.getBody();
        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        CryptoPricesStatisticsDTO dto = dtos.iterator().next();
        assertEquals(dto.getOldest().getId(), 5L);
        assertEquals(dto.getNewest().getId(), 15L);
        assertEquals(dto.getMin().getId(), 25L);
        assertEquals(dto.getMax().getId(), 35L);
        assertEquals(dto.getStatisticsDate(), now());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetHighestNormalizedCryptoRangeForDate() {
        Crypto crypto = mock(Crypto.class);
        when(crypto.getId()).thenReturn(1L);
        when(crypto.getName()).thenReturn("test");
        LocalDate date = parse("2023-04-26");
        Map<LocalDate, Set<SimpleEntry<Crypto, Float>>> ranges = newHashMap();
        ranges.put(date, newHashSet(new SimpleEntry<>(crypto, 0.2f)));
        when(service.getNormalizedRangePrices(date)).thenReturn(ranges);

        ResponseEntity<CryptoPricesNormalizedRangeDTO> result = resource.getHighestNormalizedCryptoRangeForDate(
            "2023-04-26");

        assertEquals(result.getStatusCode(), OK);
        CryptoPricesNormalizedRangeDTO dto = result.getBody();
        assertNotNull(dto);
        assertEquals(dto.getId(), 1L);
        assertEquals(dto.getName(), "test");
        assertEquals(dto.getNormalizedRange(), 0.2f);
    }

    @Test
    public void testBuildCryptoPriceRecordDTO() {
        CryptoPriceRecord cryptoPriceRecord = mock(CryptoPriceRecord.class);
        when(cryptoPriceRecord.getId()).thenReturn(1L);
        LocalDateTime time = LocalDateTime.now();
        when(cryptoPriceRecord.getTimestamp()).thenReturn(time);
        when(cryptoPriceRecord.getPrice()).thenReturn(150.0f);

        CryptoPriceRecordDTO cryptoPriceRecordDTO = resource.buildCryptoPriceRecordDTO(cryptoPriceRecord);

        assertEquals(cryptoPriceRecordDTO.getId(), 1L);
        assertEquals(cryptoPriceRecordDTO.getPrice(), 150.0f);
        assertEquals(cryptoPriceRecordDTO.getTimestamp(), time);
    }
}