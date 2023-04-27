package com.xm.services.investment.crypto.rs.dataloader.service.loader;

import com.xm.services.investment.crypto.rs.dataloader.service.loader.dataprovier.CryptoPricesDataProvider;
import com.xm.services.investment.crypto.rs.domain.Crypto;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceRecord;
import com.xm.services.investment.crypto.rs.repository.CryptoPriceRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link CryptoPricesLoaderServiceImpl}.
 */
public class CryptoPricesLoaderServiceImplTest {

    @Mock
    private CryptoPricesDataProvider dataProvider;

    @Mock
    private CryptoPriceRepository cryptoPriceRepository;

    @InjectMocks
    private CryptoPricesLoaderServiceImpl service;

    @BeforeMethod
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testLoadData() {
        Crypto crypto1 = mock(Crypto.class);
        when(crypto1.getName()).thenReturn("test1");
        Crypto crypto2 = mock(Crypto.class);
        when(crypto2.getName()).thenReturn("test2");
        CryptoPriceRecord cryptoPriceRecord1 = mock(CryptoPriceRecord.class);
        CryptoPriceRecord cryptoPriceRecord2 = mock(CryptoPriceRecord.class);
        Map<String, Collection<CryptoPriceRecord>> records = newHashMap();
        records.put("test1", newArrayList(cryptoPriceRecord1));
        records.put("test2", newArrayList(cryptoPriceRecord2));
        when(cryptoPriceRepository.getActiveCryptos()).thenReturn(newHashSet(crypto1, crypto2));
        when(dataProvider.getCurrentData(newHashSet("test1", "test2"))).thenReturn(records);
        doNothing().when(cryptoPriceRecord1).setCrypto(crypto1);
        doNothing().when(cryptoPriceRecord2).setCrypto(crypto2);
        when(cryptoPriceRepository.saveCryptoPriceRecords(newArrayList(cryptoPriceRecord1))).thenReturn(0);
        when(cryptoPriceRepository.saveCryptoPriceRecords(newArrayList(cryptoPriceRecord2))).thenReturn(1);

        Set<Crypto> result = service.loadData();

        assertEquals(result.size(), 1);
        assertEquals(result.iterator().next(), crypto2);

        verify(cryptoPriceRecord1).setCrypto(crypto1);
        verify(cryptoPriceRecord2).setCrypto(crypto2);
    }
}