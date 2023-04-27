package com.xm.services.investment.crypto.rs.domain;

import com.google.common.base.Objects;

/**
 * Crypto domain class.
 * Contains basic Crypto information.
 *
 * @author Kanstantsin_Klimianok
 */
public class Crypto {

    private long id;
    private String name;

    private byte statisticsMonthNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getStatisticsMonthNumber() {
        return statisticsMonthNumber;
    }

    public void setStatisticsMonthNumber(byte statisticsMonthNumber) {
        this.statisticsMonthNumber = statisticsMonthNumber;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Crypto crypto = (Crypto) o;
        return id == crypto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
