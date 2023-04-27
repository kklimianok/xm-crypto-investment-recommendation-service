package com.xm.services.investment.crypto.rs.rest.service;

import com.xm.services.investment.crypto.rs.domain.Crypto;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceStatistics;
import com.xm.services.investment.crypto.rs.repository.CryptoPriceStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Maps.newTreeMap;
import static com.google.common.collect.Maps.uniqueIndex;
import static com.google.common.collect.Sets.newTreeSet;
import static com.google.common.primitives.Floats.compare;
import static java.util.stream.Collectors.groupingBy;

/**
 * Service class for Crypto resources.
 *
 * @author Kanstantsin_Klimianok
 */
@Service
public class CryptoInvestmentRecommendationService {

    @Autowired
    private CryptoPriceStatsRepository cryptoPriceStatsRepository;

    public Map<LocalDate, Set<SimpleEntry<Crypto, Float>>> getNormalizedRangePrices(LocalDate date) {
        Map<LocalDate, Set<SimpleEntry<Crypto, Float>>> normalizedRangePricesMap = newTreeMap();
        Collection<CryptoPriceStatistics> statistics = date == null
            ? cryptoPriceStatsRepository.getAllStatistics()
            : cryptoPriceStatsRepository.getStatisticsForDate(date);
        Map<LocalDate, List<CryptoPriceStatistics>> dateStatistics = statistics.stream()
            .collect(groupingBy(CryptoPriceStatistics::getStatisticsDate));

        for(Entry<LocalDate, List<CryptoPriceStatistics>> entry : dateStatistics.entrySet()) {
            TreeSet<SimpleEntry<Crypto, Float>> normalizedRangePriceSet = newTreeSet(
                (e1, e2) -> compare(e1.getValue(), e2.getValue()));
            Collection<SimpleEntry<Crypto, Float>> normalizedRangePrices = transform(entry.getValue(),
                statistic -> {
                    float minValue = statistic.getMinValueRecord().getPrice();
                    float maxValue = statistic.getMaxValueRecord().getPrice();
                    return new SimpleEntry<>(statistic.getCrypto(), (maxValue - minValue) / minValue);
                });
            normalizedRangePriceSet.addAll(normalizedRangePrices);
            normalizedRangePricesMap.put(entry.getKey(), normalizedRangePriceSet.descendingSet());
        }
        return normalizedRangePricesMap;
    }

    public Map<LocalDate, CryptoPriceStatistics> getPricesStatistics(String cryptoName) {
        Collection<CryptoPriceStatistics> statistics = cryptoPriceStatsRepository.getCryptoPricesStatistics(cryptoName);
        return uniqueIndex(statistics, CryptoPriceStatistics::getStatisticsDate);
    }
}
