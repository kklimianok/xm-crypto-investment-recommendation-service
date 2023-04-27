package com.xm.services.investment.crypto.rs.repository.impl;

import com.xm.services.investment.crypto.rs.domain.Crypto;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceRecord;
import com.xm.services.investment.crypto.rs.repository.CryptoPriceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

import static java.time.ZoneId.systemDefault;
import static javax.persistence.TemporalType.TIME;

/**
 * SQL implementation of {@link CryptoPriceRepository}.
 *
 * @author Kanstantsin_Klimianok
 */
@Repository
public class CryptoPriceSQLRepositoryImpl implements CryptoPriceRepository {
    private static final String INSERT_DATA_QUERY_NAME = "INSERT_CRYPTO_PRICE_RECORD";
    private static final String ACTIVE_CRYPTOS_QUERY_NAME = "ACTIVE_CRYPTOS";
    private static final String CRYPTO_PRICES_FOR_RANGE_QUERY_NAME = "CRYPTO_PRICES_FOR_RANGE";
    private static final String CRYPTO_ID_PARAM = "cryptoId";
    private static final String TIMESTAMP_PARAM = "timestamp";
    private static final String START_DATE_PARAM = "startDate";
    private static final String PRICE_PARAM = "price";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public int saveCryptoPriceRecords(Collection<CryptoPriceRecord> cryptoPriceRecords) {
        int count = 0;
        for(CryptoPriceRecord record : cryptoPriceRecords) {
            count += entityManager.createNamedQuery(INSERT_DATA_QUERY_NAME)
                .setParameter(CRYPTO_ID_PARAM, record.getCrypto().getId())
                .setParameter(TIMESTAMP_PARAM, record.getTimestamp())
                .setParameter(PRICE_PARAM, record.getPrice())
                .executeUpdate();
        }
        return count;
    }

    @Override
    public Collection<CryptoPriceRecord> getCryptoPricesForDatePeriod(long cryptoId,
                                                                      LocalDate priceStatsDateRangeStart) {
        return entityManager.createNamedQuery(CRYPTO_PRICES_FOR_RANGE_QUERY_NAME, CryptoPriceRecord.class)
            .setParameter(CRYPTO_ID_PARAM, cryptoId)
            .setParameter(START_DATE_PARAM,
                Date.from(priceStatsDateRangeStart.atStartOfDay(systemDefault()).toInstant()), TIME)
            .getResultList();
    }

    @Override
    public Collection<Crypto> getActiveCryptos() {
        return entityManager.createNamedQuery(ACTIVE_CRYPTOS_QUERY_NAME, Crypto.class).getResultList();
    }
}
