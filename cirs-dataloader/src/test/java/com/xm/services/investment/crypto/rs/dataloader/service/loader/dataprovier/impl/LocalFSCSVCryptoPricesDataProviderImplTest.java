package com.xm.services.investment.crypto.rs.dataloader.service.loader.dataprovier.impl;

import com.xm.services.investment.crypto.rs.dataloader.service.loader.dataprovier.impl.LocalFSCSVCryptoPricesDataProviderImpl.CSVCryptoMappingStrategy;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceRecord;
import org.testng.annotations.Test;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link LocalFSCSVCryptoPricesDataProviderImpl}.
 */
public class LocalFSCSVCryptoPricesDataProviderImplTest {

    @Test
    public void testGetCurrentData() {
        LocalFSCSVCryptoPricesDataProviderImpl dataProvider = new LocalFSCSVCryptoPricesDataProviderImpl();
        dataProvider.setCsvDirectoryPath(Path.of(EMPTY).toAbsolutePath() + "/src/test/resources/csvs");

        Map<String, Collection<CryptoPriceRecord>> result = dataProvider.getCurrentData(newHashSet("BTC"));

        assertEquals(result.size(), 1);
        List<CryptoPriceRecord> records = newArrayList(result.get("BTC"));
        assertEquals(records.size(), 2);
        assertEquals(records.get(0).getPrice(), 46813.21f);
        assertEquals(records.get(1).getPrice(), 46979.61f);
    }

    @Test
    public void testGetCurrentDataForNoRecords() {
        LocalFSCSVCryptoPricesDataProviderImpl dataProvider = new LocalFSCSVCryptoPricesDataProviderImpl();
        dataProvider.setCsvDirectoryPath(Path.of(EMPTY).toAbsolutePath() + "/src/test/resources/csvs");

        Map<String, Collection<CryptoPriceRecord>> result = dataProvider.getCurrentData(newHashSet("ETH"));

        assertEquals(result.size(), 0);
    }

    @Test
    public void testGetCurrentDataForWrongCSV() {
        LocalFSCSVCryptoPricesDataProviderImpl dataProvider = new LocalFSCSVCryptoPricesDataProviderImpl();
        dataProvider.setCsvDirectoryPath(Path.of(EMPTY).toAbsolutePath() + "/src/test/resources/csvs_bad");

        Map<String, Collection<CryptoPriceRecord>> result = dataProvider.getCurrentData(newHashSet("BTC"));

        assertEquals(result.size(), 0);
    }

    @Test
    public void testPreparePath() {
        LocalFSCSVCryptoPricesDataProviderImpl dataProvider = new LocalFSCSVCryptoPricesDataProviderImpl();
        dataProvider.setCsvDirectoryPath("C://csvs");
        assertEquals(dataProvider.preparePath("BTC"),
            "C://csvs" + FileSystems.getDefault().getSeparator() + "BTC_values.csv");
    }

    @Test
    public void testPreparePathWithEndSlash() {
        LocalFSCSVCryptoPricesDataProviderImpl dataProvider = new LocalFSCSVCryptoPricesDataProviderImpl();
        dataProvider.setCsvDirectoryPath("C://csvs" + FileSystems.getDefault().getSeparator());
        assertEquals(dataProvider.preparePath("BTC"),
            "C://csvs" + FileSystems.getDefault().getSeparator() + "BTC_values.csv");
    }

    @Test
    public void testPreparePathWithNotSpecifiedPath() {
        LocalFSCSVCryptoPricesDataProviderImpl dataProvider = new LocalFSCSVCryptoPricesDataProviderImpl();
        assertEquals(dataProvider.preparePath("BTC"),
            Path.of(EMPTY).toAbsolutePath() + FileSystems.getDefault().getSeparator() + "csv" + FileSystems.getDefault()
                .getSeparator() + "BTC_values.csv");
    }

    @Test
    public void testCSVCryptoMappingStrategyConverter() throws Exception {
        CSVCryptoMappingStrategy strategy = new CSVCryptoMappingStrategy();
        assertEquals(strategy.converter.convertToRead("1641009600000"), LocalDateTime.parse("2022-01-01T05:00"));
    }
}