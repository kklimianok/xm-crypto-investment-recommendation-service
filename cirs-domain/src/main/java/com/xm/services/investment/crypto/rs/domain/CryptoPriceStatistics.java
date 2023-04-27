package com.xm.services.investment.crypto.rs.domain;

import com.google.common.base.Objects;

import java.time.LocalDate;

/**
 * Domain class for Crypto price calculated for period statistics data.
 *
 * @author Kanstantsin_Klimianok
 */
public class CryptoPriceStatistics {
    private long id;
    private Crypto crypto;
    private LocalDate statisticsDate;
    private CryptoPriceRecord oldestRecord;
    private CryptoPriceRecord newestRecord;
    private CryptoPriceRecord minValueRecord;
    private CryptoPriceRecord maxValueRecord;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Crypto getCrypto() {
        return crypto;
    }

    public void setCrypto(Crypto crypto) {
        this.crypto = crypto;
    }

    public LocalDate getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(LocalDate statisticsDate) {
        this.statisticsDate = statisticsDate;
    }

    public CryptoPriceRecord getOldestRecord() {
        return oldestRecord;
    }

    public void setOldestRecord(CryptoPriceRecord oldestRecord) {
        this.oldestRecord = oldestRecord;
    }

    public CryptoPriceRecord getNewestRecord() {
        return newestRecord;
    }

    public void setNewestRecord(CryptoPriceRecord newestRecord) {
        this.newestRecord = newestRecord;
    }

    public CryptoPriceRecord getMinValueRecord() {
        return minValueRecord;
    }

    public void setMinValueRecord(CryptoPriceRecord minValueRecord) {
        this.minValueRecord = minValueRecord;
    }

    public CryptoPriceRecord getMaxValueRecord() {
        return maxValueRecord;
    }

    public void setMaxValueRecord(CryptoPriceRecord maxValueRecord) {
        this.maxValueRecord = maxValueRecord;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        CryptoPriceStatistics that = (CryptoPriceStatistics) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
