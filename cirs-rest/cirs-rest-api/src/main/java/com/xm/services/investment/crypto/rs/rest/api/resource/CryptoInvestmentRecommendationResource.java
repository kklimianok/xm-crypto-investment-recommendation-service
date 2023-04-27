package com.xm.services.investment.crypto.rs.rest.api.resource;

import com.xm.services.investment.crypto.rs.domain.Crypto;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceRecord;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceStatistics;
import com.xm.services.investment.crypto.rs.rest.dto.CryptoPriceRecordDTO;
import com.xm.services.investment.crypto.rs.rest.dto.CryptoPricesNormalizedRangeDTO;
import com.xm.services.investment.crypto.rs.rest.dto.CryptoPricesNormalizedRangeStatisticsDTO;
import com.xm.services.investment.crypto.rs.rest.dto.CryptoPricesStatisticsDTO;
import com.xm.services.investment.crypto.rs.rest.service.CryptoInvestmentRecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.HttpStatus.OK;

/**
 * Crypto recommendations resource class.
 *
 * @author Kanstantsin_Klimianok
 */
@RestController
public class CryptoInvestmentRecommendationResource {

    private static final DateTimeFormatter DATE_FORMATTER = ofPattern("yyyy-MM-dd");

    @Autowired
    private CryptoInvestmentRecommendationService service;

    @Operation(description = "Return a descending sorted list of all the cryptos, comparing the normalized range")
    @GetMapping("/api/v1/cirs/cryptos/normalized-ranges")
    public ResponseEntity<Collection<CryptoPricesNormalizedRangeStatisticsDTO>> getCryptoNormalizedRanges(
        @Parameter(description = "Date format is yyyy-MM-dd")
        @RequestParam(value = "date", required = false) String requestDate) {
        LocalDate date = isBlank(requestDate) ? null : parse(requestDate, DATE_FORMATTER);
        Map<LocalDate, Set<SimpleEntry<Crypto, Float>>> priceDateMap = service.getNormalizedRangePrices(date);
        List<CryptoPricesNormalizedRangeStatisticsDTO> datePrices = newArrayList();
        for(Entry<LocalDate, Set<SimpleEntry<Crypto, Float>>> pricesDateEntry : priceDateMap.entrySet()) {
            List<CryptoPricesNormalizedRangeDTO> normalizedRangesPrices = newArrayList(transform(
                pricesDateEntry.getValue(), cryptoPriceEntry -> {
                    Crypto crypto = cryptoPriceEntry.getKey();
                    return new CryptoPricesNormalizedRangeDTO(crypto.getId(), crypto.getName(),
                        cryptoPriceEntry.getValue());
                }));
            datePrices.add(
                new CryptoPricesNormalizedRangeStatisticsDTO(pricesDateEntry.getKey(), normalizedRangesPrices));
        }
        return new ResponseEntity<>(datePrices, OK);
    }

    @Operation(description = "Return the oldest/newest/min/max values for a requested crypto")
    @GetMapping("/api/v1/cirs/cryptos/{cryptoName}/values")
    public ResponseEntity<Collection<CryptoPricesStatisticsDTO>> getAllStatisticsValues(@PathVariable("cryptoName")
                                                                                        String cryptoName) {
        Map<LocalDate, CryptoPriceStatistics> dateStatisticsMap = service.getPricesStatistics(cryptoName);
        List<CryptoPricesStatisticsDTO> dateStatistics = newArrayList();
        for(Entry<LocalDate, CryptoPriceStatistics> dateStatisticsEntry : dateStatisticsMap.entrySet()) {
            CryptoPriceStatistics statistics = dateStatisticsEntry.getValue();
            dateStatistics.add(CryptoPricesStatisticsDTO.builder()
                .statisticsDate(dateStatisticsEntry.getKey())
                .oldest(buildCryptoPriceRecordDTO(statistics.getOldestRecord()))
                .newest(buildCryptoPriceRecordDTO(statistics.getNewestRecord()))
                .min(buildCryptoPriceRecordDTO(statistics.getMinValueRecord()))
                .max(buildCryptoPriceRecordDTO(statistics.getMaxValueRecord()))
                .build());
        }
        return new ResponseEntity<>(dateStatistics, OK);
    }

    @Operation(description = "Return the crypto with the highest normalized range for a specific day")
    @GetMapping("/api/v1/cirs/cryptos/normalized-ranges/{date}/highest")
    public ResponseEntity<CryptoPricesNormalizedRangeDTO> getHighestNormalizedCryptoRangeForDate(
                                                            @Parameter(description = "Date format is yyyy-MM-dd")
                                                            @PathVariable("date") String requestDate) {
        LocalDate date = parse(requestDate, DATE_FORMATTER);
        SimpleEntry<Crypto, Float> highestRangeEntry = service.getNormalizedRangePrices(date)
            .get(date)
            .iterator()
            .next();
        Crypto crypto = highestRangeEntry.getKey();
        return new ResponseEntity<>(
            new CryptoPricesNormalizedRangeDTO(crypto.getId(), crypto.getName(), highestRangeEntry.getValue()), OK);
    }

    CryptoPriceRecordDTO buildCryptoPriceRecordDTO(CryptoPriceRecord cryptoPriceRecord) {
        return new CryptoPriceRecordDTO(cryptoPriceRecord.getId(), cryptoPriceRecord.getTimestamp(),
            cryptoPriceRecord.getPrice());
    }
}
