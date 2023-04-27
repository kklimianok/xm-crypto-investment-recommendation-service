package com.xm.services.investment.crypto.rs.dataloader.service.stats;

import com.xm.services.investment.crypto.rs.domain.Crypto;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceRecord;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceStatistics;
import com.xm.services.investment.crypto.rs.repository.CryptoPriceRepository;
import com.xm.services.investment.crypto.rs.repository.CryptoPriceStatsRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static com.google.common.collect.Lists.newArrayList;
import static java.time.LocalDate.now;
import static java.util.Collections.emptyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link CryptoPricesStatsCollectorServiceImpl}
 */
public class CryptoPricesStatsCollectorServiceImplTest {

    @Mock
    private CryptoPriceStatsRepository statsRepository;

    @Mock
    private CryptoPriceRepository priceRepository;

    @InjectMocks
    @Spy
    private CryptoPricesStatsCollectorServiceImpl service;

    @BeforeMethod
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testUpdateCryptoPricesStatsWithNoPricesForRange() {
        Crypto crypto = mock(Crypto.class);
        when(crypto.getId()).thenReturn(1L);
        when(crypto.getStatisticsMonthNumber()).thenReturn(((byte) 1));
        when(priceRepository.getCryptoPricesForDatePeriod(1L, now().minusMonths(1).minusDays(1))).thenReturn(
            emptyList());

        service.updateCryptoPricesStats(crypto);

        verifyNoInteractions(statsRepository);
    }

    @Test
    public void testUpdateCryptoPricesStats() {
        Crypto crypto = mock(Crypto.class);
        when(crypto.getId()).thenReturn(1L);
        when(crypto.getStatisticsMonthNumber()).thenReturn(((byte) 1));
        CryptoPriceRecord record1 = mock(CryptoPriceRecord.class);
        when(record1.getPrice()).thenReturn(155.0f);
        when(record1.getTimestamp()).thenReturn(LocalDateTime.now().minusDays(25));
        CryptoPriceRecord record2 = mock(CryptoPriceRecord.class);
        when(record2.getPrice()).thenReturn(154.0f);
        when(record2.getTimestamp()).thenReturn(LocalDateTime.now().minusDays(23));
        CryptoPriceRecord record3 = mock(CryptoPriceRecord.class);
        when(record3.getPrice()).thenReturn(149.0f);
        when(record3.getTimestamp()).thenReturn(LocalDateTime.now().minusDays(21));
        CryptoPriceRecord record4 = mock(CryptoPriceRecord.class);
        when(record4.getPrice()).thenReturn(155.0f);
        when(record4.getTimestamp()).thenReturn(LocalDateTime.now().minusDays(19));
        CryptoPriceRecord record5 = mock(CryptoPriceRecord.class);
        when(record5.getPrice()).thenReturn(158.0f);
        when(record5.getTimestamp()).thenReturn(LocalDateTime.now().minusDays(17));
        CryptoPriceRecord record6 = mock(CryptoPriceRecord.class);
        when(record6.getPrice()).thenReturn(156.0f);
        when(record6.getTimestamp()).thenReturn(LocalDateTime.now().minusDays(15));
        when(priceRepository.getCryptoPricesForDatePeriod(1L, now().minusMonths(1).minusDays(1))).thenReturn(
            newArrayList(record1, record2, record3, record4, record5, record6));
        CryptoPriceStatistics stats = mock(CryptoPriceStatistics.class);
        when(service.prepareStatistics(crypto, record1, record6, record3, record5)).thenReturn(stats);
        doNothing().when(statsRepository).saveCryptoPriceStatistics(stats);

        service.updateCryptoPricesStats(crypto);

        verify(statsRepository).saveCryptoPriceStatistics(stats);
    }

    @Test
    public void testPrepareStatistics() {
        Crypto crypto = mock(Crypto.class);
        CryptoPriceRecord oldest = mock(CryptoPriceRecord.class);
        CryptoPriceRecord newest = mock(CryptoPriceRecord.class);
        CryptoPriceRecord min = mock(CryptoPriceRecord.class);
        CryptoPriceRecord max = mock(CryptoPriceRecord.class);

        CryptoPriceStatistics result = service.prepareStatistics(crypto, oldest, newest, min, max);

        assertEquals(result.getCrypto(), crypto);
        assertEquals(result.getStatisticsDate(), now());
        assertEquals(result.getOldestRecord(), oldest);
        assertEquals(result.getNewestRecord(), newest);
        assertEquals(result.getMinValueRecord(), min);
        assertEquals(result.getMaxValueRecord(), max);
    }
}