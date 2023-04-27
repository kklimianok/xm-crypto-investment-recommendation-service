package com.xm.services.investment.crypto.rs.dataloader.service.loader.dataprovier;

import com.xm.services.investment.crypto.rs.domain.CryptoPriceRecord;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * {@link CryptoPriceRecord} data provider interface.
 * Provides data for further processing.
 *
 * @author Kanstantsin_Klimianok
 */
public interface CryptoPricesDataProvider {

    /**
     * Gets the existing records at the moment.
     *
     * @return {@link CryptoPriceRecord} collection for a crypto name map
     */
    Map<String, Collection<CryptoPriceRecord>> getCurrentData(Set<String> cryptoNames);
}
