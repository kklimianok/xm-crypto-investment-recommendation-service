package com.xm.services.investment.crypto.rs.dataloader.service;

/**
 * Basic service interface for Data Loader application.
 * Handles data load.
 *
 * @author Kanstantsin_Klimianok
 */
public interface CryptoPricesDataHandler {

    /**
     * Initialize data loading.
     */
    void handleDataLoad();

    /**
     * Initialize statistics calculation.
     */
    void handleDataStats();
}
