package com.xm.services.investment.crypto.rs.rest.dto;

import java.time.LocalDate;
import java.util.Collection;

/**
 * DTO class for Crypto normalized ranges data for a date.
 *
 * @author Kanstantsin_Klimianok
 */
public final class CryptoPricesNormalizedRangeStatisticsDTO {

    private LocalDate statisticsDate;

    private Collection<CryptoPricesNormalizedRangeDTO> normalizedRanges;

    private CryptoPricesNormalizedRangeStatisticsDTO() {
    }

    public CryptoPricesNormalizedRangeStatisticsDTO(LocalDate statisticsDate,
                                                    Collection<CryptoPricesNormalizedRangeDTO> normalizedRanges) {
        this.statisticsDate = statisticsDate;
        this.normalizedRanges = normalizedRanges;
    }

    public LocalDate getStatisticsDate() {
        return statisticsDate;
    }

    public Collection<CryptoPricesNormalizedRangeDTO> getNormalizedRanges() {
        return normalizedRanges;
    }
}
