package com.xm.services.investment.crypto.rs.rest.dto;

/**
 * DTO class for Crypto normalized range data.
 *
 * @author Kanstantsin_Klimianok
 */
public final class CryptoPricesNormalizedRangeDTO {
    private long id;

    private String name;

    private float normalizedRange;

    private CryptoPricesNormalizedRangeDTO() {
    }

    public CryptoPricesNormalizedRangeDTO(long id, String name, float normalizedRange) {
        this.id = id;
        this.name = name;
        this.normalizedRange = normalizedRange;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getNormalizedRange() {
        return normalizedRange;
    }
}
