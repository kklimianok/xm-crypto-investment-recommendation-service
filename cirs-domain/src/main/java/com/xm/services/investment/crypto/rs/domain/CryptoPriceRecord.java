package com.xm.services.investment.crypto.rs.domain;

import com.google.common.base.Objects;

import java.time.LocalDateTime;

/**
 * CryptoPriceRecord domain class.
 * Contains basic daily Crypto data.
 *
 * @author Kanstantsin_Klimianok
 */
public class CryptoPriceRecord {

    private long id;
    private Crypto crypto;
    private LocalDateTime timestamp;
    private float price;

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        CryptoPriceRecord that = (CryptoPriceRecord) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
