package com.xm.services.investment.crypto.rs.dataloader.service.stats;

import com.xm.services.investment.crypto.rs.domain.Crypto;

/**
 * Interface for Crypto Prices data stats collector.
 * Used for statistics calculation for required date range.
 *
 * @author Kanstantsin_Klimianok
 */
public interface CryptoPricesStatsCollectorService {

    /**
     * Calculates prices statistics for a range specified for {@link Crypto} and save it in DB.
     *
     * @param crypto {@link Crypto} for which statistics should be calculated.
     */
    void updateCryptoPricesStats(Crypto crypto);
}
