package com.xm.services.investment.crypto.rs.dataloader.service;

import com.xm.services.investment.crypto.rs.dataloader.service.loader.CryptoPricesLoaderService;
import com.xm.services.investment.crypto.rs.dataloader.service.stats.CryptoPricesStatsCollectorService;
import com.xm.services.investment.crypto.rs.domain.Crypto;
import com.xm.services.investment.crypto.rs.repository.CryptoPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

/**
 * Scheduler-based implementation of {@link CryptoPricesDataHandler}.
 * Run loading and statistics calculation processes at specified periodic time.
 *
 * @author Kanstantsin_Klimianok
 */
@Service
public class CryptoPricesScheduledDataHandlerImpl implements CryptoPricesDataHandler {

    private static final Logger LOGGER = getLogger(CryptoPricesScheduledDataHandlerImpl.class.getName());

    @Autowired
    private CryptoPricesLoaderService loaderService;

    @Autowired
    private CryptoPricesStatsCollectorService statsCollectorService;

    @Autowired
    private CryptoPriceRepository cryptoPriceRepository;

    @Override
    @Scheduled(cron = "0 */10 * * * *")
    public void handleDataLoad() {
        LOGGER.info("Data load started.");
        Collection<Crypto> loadedCryptos = loaderService.loadData();
        if(isNotEmpty(loadedCryptos)) { //new data found, need to recalculate statistics
            LOGGER.info("Triggering stats recalculation for " + loadedCryptos.size() + " cryptos");
            for(Crypto crypto : loadedCryptos) {
                statsCollectorService.updateCryptoPricesStats(crypto);
            }
        }
    }

    @Override
    @Scheduled(cron = "0 0 */24 * * *") //even if no new records appeared, we need daily stats
    public void handleDataStats() {
        LOGGER.info("Periodic prices statistics calculation started.");
        Collection<Crypto> activeCryptos = cryptoPriceRepository.getActiveCryptos();
        for(Crypto crypto : activeCryptos) {
            statsCollectorService.updateCryptoPricesStats(crypto);
        }
    }
}
