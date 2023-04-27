package com.xm.services.investment.crypto.rs.dataloader.service.loader;

import com.xm.services.investment.crypto.rs.domain.Crypto;

import java.util.Set;

/**
 * Data loader service interface.
 *
 * @author Kanstantsin_Klimianok
 */
public interface CryptoPricesLoaderService {

    /**
     * Execute data loading and return Cryptos which have new data.
     *
     * @return Set of {@link Crypto} objects for which data was updated.
     */
    Set<Crypto> loadData();
}
