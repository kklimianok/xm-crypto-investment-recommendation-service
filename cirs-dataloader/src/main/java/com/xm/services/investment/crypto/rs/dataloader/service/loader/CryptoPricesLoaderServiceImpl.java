package com.xm.services.investment.crypto.rs.dataloader.service.loader;

import com.xm.services.investment.crypto.rs.dataloader.service.loader.dataprovier.CryptoPricesDataProvider;
import com.xm.services.investment.crypto.rs.domain.Crypto;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceRecord;
import com.xm.services.investment.crypto.rs.repository.CryptoPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import static com.google.common.collect.Maps.uniqueIndex;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.logging.Logger.getLogger;

/**
 * CryptoPricesLoaderService implementation.
 * Loads raw records and save it in DB.
 *
 * @author Kanstantsin_Klimianok
 */
@Service
public class CryptoPricesLoaderServiceImpl implements CryptoPricesLoaderService {

    private static final Logger LOGGER = getLogger(CryptoPricesLoaderServiceImpl.class.getName());

    @Autowired
    private CryptoPricesDataProvider dataProvider;
    @Autowired
    private CryptoPriceRepository cryptoPriceRepository;

    public Set<Crypto> loadData() {
        Set<Crypto> savedCryptos = newHashSet();
        Map<String, Crypto> activeCryptos = uniqueIndex(cryptoPriceRepository.getActiveCryptos(), Crypto::getName);
        LOGGER.info("Data load processing started for " + activeCryptos.keySet());
        Map<String, Collection<CryptoPriceRecord>> readCryptos = dataProvider.getCurrentData(activeCryptos.keySet());
        for(Entry<String, Collection<CryptoPriceRecord>> cryptoPriceEntry : readCryptos.entrySet()) {
            Crypto crypto = activeCryptos.get(cryptoPriceEntry.getKey());
            Collection<CryptoPriceRecord> cryptoPrices = cryptoPriceEntry.getValue();
            cryptoPrices.forEach(cryptoPriceRecord -> cryptoPriceRecord.setCrypto(crypto));
            int savedCount = cryptoPriceRepository.saveCryptoPriceRecords(cryptoPrices);
            if(savedCount > 0) {
                savedCryptos.add(crypto);
                LOGGER.info("Saved " + savedCount + " new records for " + cryptoPriceEntry.getKey());
            }
        }
        return savedCryptos;
    }
}
