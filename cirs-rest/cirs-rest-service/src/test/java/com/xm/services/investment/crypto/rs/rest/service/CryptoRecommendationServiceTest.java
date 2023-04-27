package com.xm.services.investment.crypto.rs.rest.service;

import com.xm.services.investment.crypto.rs.domain.Crypto;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceRecord;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceStatistics;
import com.xm.services.investment.crypto.rs.repository.CryptoPriceStatsRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.time.LocalDate.now;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link CryptoInvestmentRecommendationService}.
 */
public class CryptoRecommendationServiceTest {

    @InjectMocks
    private CryptoInvestmentRecommendationService service;

    @Mock
    private CryptoPriceStatsRepository cryptoPriceStatsRepository;

    @BeforeMethod
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testGetNormalizedRangePrices() {
        CryptoPriceStatistics statistics = new CryptoPriceStatistics();
        Crypto crypto = new Crypto();
        crypto.setId(1);
        crypto.setName("Test Crypto");
        statistics.setCrypto(crypto);
        statistics.setId(2);
        statistics.setStatisticsDate(now().minusDays(1));
        CryptoPriceRecord minRecord = new CryptoPriceRecord();
        minRecord.setPrice(16.0f);
        minRecord.setId(3);
        CryptoPriceRecord maxRecord = new CryptoPriceRecord();
        maxRecord.setPrice(20.0f);
        maxRecord.setId(4);
        statistics.setMinValueRecord(minRecord);
        statistics.setMaxValueRecord(maxRecord);
        when(cryptoPriceStatsRepository.getStatisticsForDate(now())).thenReturn(newArrayList(statistics));
        Map<LocalDate, Set<SimpleEntry<Crypto, Float>>> resultMap = service.getNormalizedRangePrices(now());

        assertEquals(resultMap.size(), 1);
        assertEquals(resultMap.entrySet().iterator().next().getKey(), now().minusDays(1));
        Set<SimpleEntry<Crypto, Float>> resultValue = resultMap.entrySet().iterator().next().getValue();
        assertEquals(resultValue.size(), 1);
        assertEquals(resultValue.iterator().next().getKey(), crypto);
        assertEquals(resultValue.iterator().next().getValue(), 0.25f);
    }

    @Test
    public void testGetNormalizedRangePricesForAllDate() {
        CryptoPriceStatistics statistics = new CryptoPriceStatistics();
        Crypto crypto = new Crypto();
        crypto.setId(1);
        crypto.setName("Test Crypto");
        statistics.setCrypto(crypto);
        statistics.setId(2);
        statistics.setStatisticsDate(now().minusDays(1));
        CryptoPriceRecord minRecord = new CryptoPriceRecord();
        minRecord.setPrice(16.0f);
        minRecord.setId(3);
        CryptoPriceRecord maxRecord = new CryptoPriceRecord();
        maxRecord.setPrice(20.0f);
        maxRecord.setId(4);
        statistics.setMinValueRecord(minRecord);
        statistics.setMaxValueRecord(maxRecord);
        when(cryptoPriceStatsRepository.getAllStatistics()).thenReturn(newArrayList(statistics));
        Map<LocalDate, Set<SimpleEntry<Crypto, Float>>> resultMap = service.getNormalizedRangePrices(null);

        assertEquals(resultMap.size(), 1);
        assertEquals(resultMap.entrySet().iterator().next().getKey(), now().minusDays(1));
        Set<SimpleEntry<Crypto, Float>> resultValue = resultMap.entrySet().iterator().next().getValue();
        assertEquals(resultValue.size(), 1);
        assertEquals(resultValue.iterator().next().getKey(), crypto);
        assertEquals(resultValue.iterator().next().getValue(), 0.25f);
    }

    @Test
    public void testGetPricesStatistics() {
        CryptoPriceStatistics statistics1 = new CryptoPriceStatistics();
        statistics1.setId(1);
        statistics1.setStatisticsDate(now());
        CryptoPriceStatistics statistics2 = new CryptoPriceStatistics();
        statistics2.setId(2);
        statistics2.setStatisticsDate(now().minusDays(2));
        when(cryptoPriceStatsRepository.getCryptoPricesStatistics("test")).thenReturn(
            newArrayList(statistics1, statistics2));
        Map<LocalDate, CryptoPriceStatistics> expectedMap = newHashMap();
        expectedMap.put(now(), statistics1);
        expectedMap.put(now().minusDays(2), statistics2);

        assertEquals(service.getPricesStatistics("test"), expectedMap);
    }
}