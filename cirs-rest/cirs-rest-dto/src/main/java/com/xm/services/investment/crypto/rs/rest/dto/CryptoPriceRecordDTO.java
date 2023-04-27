package com.xm.services.investment.crypto.rs.rest.dto;

import java.time.LocalDateTime;

/**
 * DTO class for Crypto price record.
 *
 * @author Kanstantsin_Klimianok
 */
public final class CryptoPriceRecordDTO {
    private long id;

    private LocalDateTime timestamp;

    private float price;

    private CryptoPriceRecordDTO() {
    }

    public CryptoPriceRecordDTO(long id, LocalDateTime timestamp, float price) {
        this.id = id;
        this.timestamp = timestamp;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public float getPrice() {
        return price;
    }
}
