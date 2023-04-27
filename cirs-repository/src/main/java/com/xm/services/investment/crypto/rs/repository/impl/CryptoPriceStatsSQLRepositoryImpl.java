package com.xm.services.investment.crypto.rs.repository.impl;

import com.xm.services.investment.crypto.rs.domain.CryptoPriceStatistics;
import com.xm.services.investment.crypto.rs.repository.CryptoPriceStatsRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.Collection;

/**
 * SQL implementation of CryptoPriceStatsRepository.
 *
 * @author Kanstantsin_Klimianok
 */
@Repository
public class CryptoPriceStatsSQLRepositoryImpl implements CryptoPriceStatsRepository {

    private static final String INSERT_DATA_QUERY_NAME = "INSERT_CRYPTO_PRICE_STATS";
    private static final String CRYPTO_PRICE_STATISTICS_QUERY_NAME = "CRYPTO_PRICE_STATISTICS";
    private static final String STATISTICS_FOR_DATE_QUERY_NAME = "DATE_STATISTICS";
    private static final String ALL_STATISTICS_QUERY_NAME = "ALL_STATISTICS";
    private static final String CRYPTO_NAME_PARAM = "cryptoName";
    private static final String CRYPTO_ID_PARAM = "cryptoId";
    private static final String DATE_PARAM = "date";
    private static final String OLDEST_RECORD_ID_PARAM = "oldestRecordId";
    private static final String NEWEST_RECORD_ID_PARAM = "newestRecordId";
    private static final String MIN_PRICE_RECORD_ID_PARAM = "minRecordId";
    private static final String MAX_PRICE_RECORD_ID_PARAM = "maxRecordId";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveCryptoPriceStatistics(CryptoPriceStatistics statistics) {
        entityManager.createNamedQuery(INSERT_DATA_QUERY_NAME)
            .setParameter(CRYPTO_ID_PARAM, statistics.getCrypto().getId())
            .setParameter(DATE_PARAM, statistics.getStatisticsDate())
            .setParameter(OLDEST_RECORD_ID_PARAM, statistics.getOldestRecord().getId())
            .setParameter(NEWEST_RECORD_ID_PARAM, statistics.getNewestRecord().getId())
            .setParameter(MIN_PRICE_RECORD_ID_PARAM, statistics.getMinValueRecord().getId())
            .setParameter(MAX_PRICE_RECORD_ID_PARAM, statistics.getMaxValueRecord().getId())
            .executeUpdate();
    }

    @Override
    public Collection<CryptoPriceStatistics> getCryptoPricesStatistics(String cryptoName) {
        return entityManager.createNamedQuery(CRYPTO_PRICE_STATISTICS_QUERY_NAME, CryptoPriceStatistics.class)
            .setParameter(CRYPTO_NAME_PARAM, cryptoName)
            .getResultList();
    }

    @Override
    public Collection<CryptoPriceStatistics> getStatisticsForDate(LocalDate date) {
        return entityManager.createNamedQuery(STATISTICS_FOR_DATE_QUERY_NAME, CryptoPriceStatistics.class)
            .setParameter(DATE_PARAM, date)
            .getResultList();
    }

    @Override
    public Collection<CryptoPriceStatistics> getAllStatistics() {
        return entityManager.createNamedQuery(ALL_STATISTICS_QUERY_NAME, CryptoPriceStatistics.class)
            .getResultList();
    }
}
