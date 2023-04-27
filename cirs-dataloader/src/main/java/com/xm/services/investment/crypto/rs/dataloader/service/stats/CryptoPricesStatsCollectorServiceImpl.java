package com.xm.services.investment.crypto.rs.dataloader.service.stats;

import com.xm.services.investment.crypto.rs.domain.Crypto;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceRecord;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceStatistics;
import com.xm.services.investment.crypto.rs.repository.CryptoPriceRepository;
import com.xm.services.investment.crypto.rs.repository.CryptoPriceStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.TreeSet;
import java.util.logging.Logger;

import static com.google.common.collect.Sets.newTreeSet;
import static java.time.LocalDate.now;
import static java.util.Comparator.comparing;
import static java.util.logging.Logger.getLogger;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * Implementation of {@link CryptoPricesStatsCollectorService}.
 * Calculates statistics for the range specified in Crypto and save it in DB.
 *
 * @author Kanstantsin_Klimianok
 */
@Service
public class CryptoPricesStatsCollectorServiceImpl implements CryptoPricesStatsCollectorService {

    private static final Logger LOGGER = getLogger(CryptoPricesStatsCollectorServiceImpl.class.getName());

    private static final int ONE = 1;

    @Autowired
    private CryptoPriceStatsRepository statsRepository;

    @Autowired
    private CryptoPriceRepository priceRepository;

    @Override
    public void updateCryptoPricesStats(Crypto crypto) {
        LOGGER.info("Statistics recalculate started for " + crypto.getName());
        Collection<CryptoPriceRecord> cryptoPricesForRange = priceRepository.getCryptoPricesForDatePeriod(
            crypto.getId(), now().minusMonths(crypto.getStatisticsMonthNumber())
                .minusDays(ONE)); // today minus period minus day, because today should be considered
        if(isEmpty(cryptoPricesForRange)) {
            return;
        }
        // sorted by timestamp set
        TreeSet<CryptoPriceRecord> dateOrderedRecords = newTreeSet(comparing(CryptoPriceRecord::getTimestamp));
        dateOrderedRecords.addAll(cryptoPricesForRange);

        // sorted by price set
        TreeSet<CryptoPriceRecord> priceOrderedRecords = newTreeSet(comparing(CryptoPriceRecord::getPrice));
        priceOrderedRecords.addAll(cryptoPricesForRange);

        statsRepository.saveCryptoPriceStatistics(
            prepareStatistics(crypto, dateOrderedRecords.first(), dateOrderedRecords.last(),
                priceOrderedRecords.first(), priceOrderedRecords.last()));
    }

    CryptoPriceStatistics prepareStatistics(Crypto crypto, CryptoPriceRecord oldest,
                                            CryptoPriceRecord newest, CryptoPriceRecord min,
                                            CryptoPriceRecord max) {
        CryptoPriceStatistics statistics = new CryptoPriceStatistics();
        statistics.setCrypto(crypto);
        statistics.setStatisticsDate(now());
        statistics.setOldestRecord(oldest);
        statistics.setNewestRecord(newest);
        statistics.setMinValueRecord(min);
        statistics.setMaxValueRecord(max);
        return statistics;
    }
}
