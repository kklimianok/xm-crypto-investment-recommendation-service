package com.xm.services.investment.crypto.rs.dataloader.service.loader.dataprovier.impl;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.bean.BeanFieldSingleValue;
import com.opencsv.bean.CsvConverter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.xm.services.investment.crypto.rs.dataloader.service.loader.dataprovier.CryptoPricesDataProvider;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceRecord;
import org.apache.commons.collections4.ListValuedMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.Logger;

import static com.google.common.collect.Maps.newHashMap;
import static java.lang.Long.parseLong;
import static java.time.Instant.ofEpochMilli;
import static java.util.logging.Logger.getLogger;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static org.apache.commons.lang3.StringUtils.endsWith;
import static org.apache.commons.lang3.StringUtils.upperCase;

/**
 * {@link CryptoPricesDataProvider} implementation for CSVs on local file system.
 * Reads CSV data from local file system and parse it into {@link CryptoPriceRecord}.
 *
 * @author Kanstantsin_Klimianok
 */
@Component
public class LocalFSCSVCryptoPricesDataProviderImpl implements CryptoPricesDataProvider {

    private static final Logger LOGGER = getLogger(LocalFSCSVCryptoPricesDataProviderImpl.class.getName());

    private static final String CSV_FILE_NAME_TEMPLATE = "{0}_values.csv";

    private static final String TIMESTAMP_FIELD_NAME = "timestamp";

    private static final String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();

    private static final String DEFAULT_BASE_PATH = Path.of(EMPTY).toAbsolutePath().toString() + FILE_SEPARATOR + "csv";

    @Value("${csvDirectoryPath:}")
    private String csvDirectoryPath;

    /**
     * Custom CSV Mapping strategy to parse Long timestamp value into LocalDateTime field.
     * Override fields map and set the correct converter for "timestamp" field.
     */
    static class CSVCryptoMappingStrategy extends HeaderColumnNameMappingStrategy<CryptoPriceRecord> {
        CsvConverter converter = new AbstractCsvConverter() {
            @Override
            public LocalDateTime convertToRead(String value) {
                return LocalDateTime.ofInstant(ofEpochMilli(parseLong(value)), TimeZone.getDefault().toZoneId());
            }
        };

        @Override
        protected void loadUnadornedFieldMap(ListValuedMap<Class<?>, Field> fields) {
            super.loadUnadornedFieldMap(fields);
            try {
                fieldMap.put(TIMESTAMP_FIELD_NAME.toUpperCase(), new BeanFieldSingleValue<>(CryptoPriceRecord.class,
                    CryptoPriceRecord.class.getDeclaredField(TIMESTAMP_FIELD_NAME), true, null, converter, EMPTY,
                    EMPTY));
            } catch(NoSuchFieldException e) {
                LOGGER.severe(TIMESTAMP_FIELD_NAME
                    + " is not found in CryptoPriceRecord class. Timestamp won't be parsed. Details:\n"
                    + e.getMessage());
            }
        }
    }

    void setCsvDirectoryPath(String csvDirectoryPath) {
        this.csvDirectoryPath = csvDirectoryPath;
    }

    @Override
    public Map<String, Collection<CryptoPriceRecord>> getCurrentData(Set<String> cryptoNames) {
        Map<String, Collection<CryptoPriceRecord>> cryptoRecordsMap = newHashMap();
        for(String cryptoName : cryptoNames) {
            String csvRecordsFile = preparePath(cryptoName);
            CSVCryptoMappingStrategy str = new CSVCryptoMappingStrategy();
            str.setType(CryptoPriceRecord.class);
            try (Reader csvReader = new FileReader(csvRecordsFile)) {
                cryptoRecordsMap.put(cryptoName,
                    new CsvToBeanBuilder<CryptoPriceRecord>(csvReader)
                        .withType(CryptoPriceRecord.class)
                        .withMappingStrategy(str)
                        .build()
                        .parse());
            } catch(Exception e) {
                LOGGER.warning("Error processing \"" + csvRecordsFile + "\". Reason: " + e.getMessage());
            }

        }
        return cryptoRecordsMap;
    }

    String preparePath(String cryptoName) {
        String csvPath = defaultIfBlank(csvDirectoryPath, DEFAULT_BASE_PATH)
            + (endsWith(csvDirectoryPath, FILE_SEPARATOR) ? EMPTY : FILE_SEPARATOR);
        return csvPath + MessageFormat.format(CSV_FILE_NAME_TEMPLATE, upperCase(cryptoName));
    }

}

