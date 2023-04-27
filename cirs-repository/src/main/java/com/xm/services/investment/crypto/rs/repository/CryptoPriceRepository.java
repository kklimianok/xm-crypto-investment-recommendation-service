package com.xm.services.investment.crypto.rs.repository;

import com.xm.services.investment.crypto.rs.domain.Crypto;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceRecord;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Crypto repository interface.
 * Provides Crypto data manipulation.
 *
 * @author Kanstantsin_Klimianok
 */
public interface CryptoPriceRepository {
    /**
     * Stores the timestamped crypto price records.
     *
     * @param cryptoPriceRecords timestamped crypto price records
     * @return number of saved records
     */
    int saveCryptoPriceRecords(Collection<CryptoPriceRecord> cryptoPriceRecords);

    /**
     * Get the price records for a specific date range.
     *
     * @param cryptoId Crypto's id
     * @param priceStatsDateRangeStart date range start param
     * @return collection of price records
     */
    Collection<CryptoPriceRecord> getCryptoPricesForDatePeriod(long cryptoId, LocalDate priceStatsDateRangeStart);

    /**
     * Get all active (enabled) Cryptos.
     *
     * @return collection of Crypto
     */
    Collection<Crypto> getActiveCryptos();
}
