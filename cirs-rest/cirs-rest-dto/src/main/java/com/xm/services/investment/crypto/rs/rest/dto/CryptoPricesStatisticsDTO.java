package com.xm.services.investment.crypto.rs.rest.dto;

import java.time.LocalDate;

/**
 * DTO class for Crypto statistics values.
 *
 * @author Kanstantsin_Klimianok
 */
public final class CryptoPricesStatisticsDTO {

    private LocalDate statisticsDate;

    private CryptoPriceRecordDTO oldest;

    private CryptoPriceRecordDTO newest;

    private CryptoPriceRecordDTO min;

    private CryptoPriceRecordDTO max;

    private CryptoPricesStatisticsDTO() {
    }

    private CryptoPricesStatisticsDTO(Builder builder) {
        statisticsDate = builder.statisticsDate;
        oldest = builder.oldest;
        newest = builder.newest;
        min = builder.min;
        max = builder.max;
    }

    public LocalDate getStatisticsDate() {
        return statisticsDate;
    }

    public CryptoPriceRecordDTO getOldest() {
        return oldest;
    }

    public CryptoPriceRecordDTO getNewest() {
        return newest;
    }

    public CryptoPriceRecordDTO getMin() {
        return min;
    }

    public CryptoPriceRecordDTO getMax() {
        return max;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private LocalDate statisticsDate;

        private CryptoPriceRecordDTO oldest;

        private CryptoPriceRecordDTO newest;

        private CryptoPriceRecordDTO min;

        private CryptoPriceRecordDTO max;

        public Builder statisticsDate(LocalDate date) {
            this.statisticsDate = date;
            return this;
        }

        public Builder oldest(CryptoPriceRecordDTO oldest) {
            this.oldest = oldest;
            return this;
        }

        public Builder newest(CryptoPriceRecordDTO newest) {
            this.newest = newest;
            return this;
        }

        public Builder min(CryptoPriceRecordDTO min) {
            this.min = min;
            return this;
        }

        public Builder max(CryptoPriceRecordDTO max) {
            this.max = max;
            return this;
        }

        public CryptoPricesStatisticsDTO build() {
            return new CryptoPricesStatisticsDTO(this);
        }
    }
}
