package com.xm.services.investment.crypto.rs.dataloader.service;

import com.xm.services.investment.crypto.rs.dataloader.service.loader.CryptoPricesLoaderService;
import com.xm.services.investment.crypto.rs.dataloader.service.stats.CryptoPricesStatsCollectorService;
import com.xm.services.investment.crypto.rs.domain.Crypto;
import com.xm.services.investment.crypto.rs.repository.CryptoPriceRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.emptySet;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Unit tests for {@link CryptoPricesScheduledDataHandlerImpl}
 */
public class CryptoPricesScheduledDataHandlerImplTest {

    @Mock
    private CryptoPricesLoaderService loaderService;

    @Mock
    private CryptoPricesStatsCollectorService statsCollectorService;

    @Mock
    private CryptoPriceRepository cryptoPriceRepository;

    @InjectMocks
    private CryptoPricesScheduledDataHandlerImpl handler;

    @BeforeMethod
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testHandleDataLoad() {
        Crypto crypto = Mockito.mock(Crypto.class);
        when(loaderService.loadData()).thenReturn(newHashSet(crypto));
        doNothing().when(statsCollectorService).updateCryptoPricesStats(crypto);

        handler.handleDataLoad();

        verify(statsCollectorService).updateCryptoPricesStats(crypto);
    }

    @Test
    public void testHandleDataLoadWithNoNewData() {
        when(loaderService.loadData()).thenReturn(emptySet());

        handler.handleDataLoad();

        verifyNoInteractions(statsCollectorService);
    }

    @Test
    public void testHandleDataStats() {
        Crypto crypto = Mockito.mock(Crypto.class);
        when(cryptoPriceRepository.getActiveCryptos()).thenReturn(newHashSet(crypto));
        doNothing().when(statsCollectorService).updateCryptoPricesStats(crypto);

        handler.handleDataStats();

        verify(statsCollectorService).updateCryptoPricesStats(crypto);
    }

    @Test
    public void testHandleDataStatsWithNoActiveCryptos() {
        when(cryptoPriceRepository.getActiveCryptos()).thenReturn(emptySet());

        handler.handleDataStats();

        verifyNoInteractions(statsCollectorService);
    }
}