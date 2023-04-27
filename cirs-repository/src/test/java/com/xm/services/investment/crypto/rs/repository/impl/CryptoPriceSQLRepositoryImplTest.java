package com.xm.services.investment.crypto.rs.repository.impl;

import com.xm.services.investment.crypto.rs.domain.Crypto;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceRecord;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

import static java.time.LocalDate.now;
import static java.time.ZoneId.systemDefault;
import static java.util.Collections.singletonList;
import static javax.persistence.TemporalType.TIME;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link CryptoPriceSQLRepositoryImpl}.
 */
public class CryptoPriceSQLRepositoryImplTest {

    @InjectMocks
    private CryptoPriceSQLRepositoryImpl repository;

    @Mock
    private EntityManager entityManager;

    @BeforeMethod
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testSaveCryptoPriceRecords() {
        Crypto crypto = mock(Crypto.class);
        when(crypto.getId()).thenReturn(111L);
        CryptoPriceRecord cryptoPriceRecord = mock(CryptoPriceRecord.class);
        when(cryptoPriceRecord.getCrypto()).thenReturn(crypto);
        LocalDateTime time = LocalDateTime.now();
        when(cryptoPriceRecord.getTimestamp()).thenReturn(time);
        when(cryptoPriceRecord.getPrice()).thenReturn(1.0f);
        Query executeQuery = mock(Query.class);
        when(entityManager.createNamedQuery("INSERT_CRYPTO_PRICE_RECORD")).thenReturn(executeQuery);
        when(executeQuery.setParameter("cryptoId", 111L)).thenReturn(executeQuery);
        when(executeQuery.setParameter("timestamp", time)).thenReturn(executeQuery);
        when(executeQuery.setParameter("price", 1.0f)).thenReturn(executeQuery);
        when(executeQuery.executeUpdate()).thenReturn(1);

        assertEquals(repository.saveCryptoPriceRecords(singletonList(cryptoPriceRecord)), 1);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetActiveCryptos() {
        Crypto crypto = mock(Crypto.class);
        TypedQuery<Crypto> query = mock(TypedQuery.class);
        when(entityManager.createNamedQuery("ACTIVE_CRYPTOS", Crypto.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(singletonList(crypto));

        Collection<Crypto> result = repository.getActiveCryptos();

        assertEquals(result.size(), 1);
        assertEquals(result.iterator().next(), crypto);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetCryptoPricesForDatePeriod() {
        LocalDate date = now();
        Date dateLocalDate = Date.from(date.atStartOfDay(systemDefault()).toInstant());
        CryptoPriceRecord cryptoPriceRecord = mock(CryptoPriceRecord.class);
        TypedQuery<CryptoPriceRecord> query = mock(TypedQuery.class);
        when(entityManager.createNamedQuery("CRYPTO_PRICES_FOR_RANGE", CryptoPriceRecord.class)).thenReturn(query);
        when(query.setParameter("cryptoId", 1L)).thenReturn(query);
        when(query.setParameter("startDate", dateLocalDate, TIME)).thenReturn(query);
        when(query.getResultList()).thenReturn(singletonList(cryptoPriceRecord));

        Collection<CryptoPriceRecord> result = repository.getCryptoPricesForDatePeriod(1, date);

        assertEquals(result.size(), 1);
        assertEquals(result.iterator().next(), cryptoPriceRecord);
    }
}