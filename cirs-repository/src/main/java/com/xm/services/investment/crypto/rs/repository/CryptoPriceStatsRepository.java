package com.xm.services.investment.crypto.rs.repository;

import com.xm.services.investment.crypto.rs.domain.CryptoPriceStatistics;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Crypto statistics repository interface.
 * Provides calculated Crypto statistics data manipulation.
 *
 * @author Kanstantsin_Klimianok
 */
public interface CryptoPriceStatsRepository {

    void saveCryptoPriceStatistics(CryptoPriceStatistics statistics);

    Collection<CryptoPriceStatistics> getCryptoPricesStatistics(String cryptoName);

    Collection<CryptoPriceStatistics> getStatisticsForDate(LocalDate date);

    Collection<CryptoPriceStatistics> getAllStatistics();
}
