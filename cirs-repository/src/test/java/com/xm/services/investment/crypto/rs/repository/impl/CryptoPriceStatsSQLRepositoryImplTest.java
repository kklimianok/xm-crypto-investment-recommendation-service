package com.xm.services.investment.crypto.rs.repository.impl;

import com.xm.services.investment.crypto.rs.domain.Crypto;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceRecord;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceStatistics;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.Collection;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link CryptoPriceStatsSQLRepositoryImpl}.
 */
public class CryptoPriceStatsSQLRepositoryImplTest {

    @InjectMocks
    private CryptoPriceStatsSQLRepositoryImpl repository;

    @Mock
    private EntityManager entityManager;

    @BeforeMethod
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testSaveCryptoPriceStatistics() {
        Crypto crypto = mock(Crypto.class);
        when(crypto.getId()).thenReturn(111L);
        CryptoPriceStatistics statistics = mock(CryptoPriceStatistics.class);
        when(statistics.getCrypto()).thenReturn(crypto);
        LocalDate date = LocalDate.now();
        when(statistics.getStatisticsDate()).thenReturn(date);
        CryptoPriceRecord oldest = mock(CryptoPriceRecord.class);
        when(oldest.getId()).thenReturn(123L);
        when(statistics.getOldestRecord()).thenReturn(oldest);
        CryptoPriceRecord newest = mock(CryptoPriceRecord.class);
        when(newest.getId()).thenReturn(321L);
        when(statistics.getNewestRecord()).thenReturn(newest);
        CryptoPriceRecord min = mock(CryptoPriceRecord.class);
        when(min.getId()).thenReturn(235L);
        when(statistics.getMinValueRecord()).thenReturn(min);
        CryptoPriceRecord max = mock(CryptoPriceRecord.class);
        when(max.getId()).thenReturn(367L);
        when(statistics.getMaxValueRecord()).thenReturn(max);

        Query executeQuery = mock(Query.class);
        when(entityManager.createNamedQuery("INSERT_CRYPTO_PRICE_STATS")).thenReturn(executeQuery);
        when(executeQuery.setParameter("cryptoId", 111L)).thenReturn(executeQuery);
        when(executeQuery.setParameter("date", date)).thenReturn(executeQuery);
        when(executeQuery.setParameter("oldestRecordId", 123L)).thenReturn(executeQuery);
        when(executeQuery.setParameter("newestRecordId", 321L)).thenReturn(executeQuery);
        when(executeQuery.setParameter("minRecordId", 235L)).thenReturn(executeQuery);
        when(executeQuery.setParameter("maxRecordId", 367L)).thenReturn(executeQuery);
        when(executeQuery.executeUpdate()).thenReturn(1);

        repository.saveCryptoPriceStatistics(statistics);

        verify(executeQuery).executeUpdate();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetCryptoPricesStatistics() {
        CryptoPriceStatistics statistics = mock(CryptoPriceStatistics.class);
        TypedQuery<CryptoPriceStatistics> query = mock(TypedQuery.class);
        when(entityManager.createNamedQuery("CRYPTO_PRICE_STATISTICS", CryptoPriceStatistics.class)).thenReturn(query);
        TypedQuery<CryptoPriceStatistics> parameterizedQuery = mock(TypedQuery.class);
        when(query.setParameter("cryptoName", "test crypto")).thenReturn(parameterizedQuery);
        when(parameterizedQuery.getResultList()).thenReturn(singletonList(statistics));

        Collection<CryptoPriceStatistics> result = repository.getCryptoPricesStatistics("test crypto");

        assertEquals(result.size(), 1);
        assertEquals(result.iterator().next(), statistics);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetStatisticsForDate() {
        CryptoPriceStatistics statistics = mock(CryptoPriceStatistics.class);
        TypedQuery<CryptoPriceStatistics> query = mock(TypedQuery.class);
        when(entityManager.createNamedQuery("DATE_STATISTICS", CryptoPriceStatistics.class)).thenReturn(query);
        TypedQuery<CryptoPriceStatistics> parameterizedQuery = mock(TypedQuery.class);
        LocalDate date = LocalDate.now();
        when(query.setParameter("date", date)).thenReturn(parameterizedQuery);
        when(parameterizedQuery.getResultList()).thenReturn(singletonList(statistics));

        Collection<CryptoPriceStatistics> result = repository.getStatisticsForDate(date);

        assertEquals(result.size(), 1);
        assertEquals(result.iterator().next(), statistics);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetAllStatistics() {
        CryptoPriceStatistics statistics = mock(CryptoPriceStatistics.class);
        TypedQuery<CryptoPriceStatistics> query = mock(TypedQuery.class);
        when(entityManager.createNamedQuery("ALL_STATISTICS", CryptoPriceStatistics.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(singletonList(statistics));

        Collection<CryptoPriceStatistics> result = repository.getAllStatistics();

        assertEquals(result.size(), 1);
        assertEquals(result.iterator().next(), statistics);
    }
}