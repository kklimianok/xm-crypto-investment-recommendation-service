package com.xm.services.investment.crypto.rs.dataloader.service.integration;

import com.xm.services.investment.crypto.rs.dataloader.service.CryptoPricesDataHandler;
import com.xm.services.investment.crypto.rs.domain.CryptoPriceStatistics;
import com.xm.services.investment.crypto.rs.repository.CryptoPriceStatsRepository;
import org.h2.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Collection;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Test
@ContextConfiguration(locations = {"classpath:cirs-dataloader-test.xml"})
public class DataLoaderIntegrationTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private CryptoPricesDataHandler handler;

    @Autowired
    private CryptoPriceStatsRepository cryptoPriceStatsRepository;

    private Connection connection;
    private Statement statement;

    @BeforeTest
    public void setUp() throws Exception {
        final Driver driver = new Driver();
        connection = driver.connect("jdbc:h2:mem:cirs_test;USER=test;PASSWORD=test;MODE=PostgreSQL", null);
        statement = connection.createStatement();

        createDatabaseObjects();
    }

    @AfterMethod
    public void tearDown() {
        try {
            statement.close();
        } catch(final SQLException se) {
            se.printStackTrace();
        }
        try {
            connection.close();
        } catch(final SQLException se) {
            se.printStackTrace();
        }

        statement = null;
        connection = null;
    }

    private void createDatabaseObjects()
        throws SQLException {
        statement.execute("CREATE TABLE crypto (\n"
            + "   id SERIAL PRIMARY KEY,\n"
            + "   name TEXT NOT NULL,\n"
            + "   active BOOL NOT NULL DEFAULT true,\n"
            + "   statistics_month SMALLINT NOT NULL DEFAULT 1\n"
            + ");");
        statement.execute("ALTER TABLE crypto\n"
            + "ADD CONSTRAINT unique_crypto_name\n"
            + "UNIQUE(name);");
        statement.execute("CREATE TABLE crypto_price_record (\n"
            + "   id  bigserial PRIMARY KEY,\n"
            + "   crypto_id INTEGER NOT NULL,\n"
            + "   timestamp TIMESTAMP NOT NULL,\n"
            + "   price REAL NOT NULL\n"
            + ");");
        statement.execute("ALTER TABLE crypto_price_record\n"
            + "ADD CONSTRAINT unique_crypto_price_record_for_time\n"
            + "UNIQUE(crypto_id, timestamp);");
        statement.execute("CREATE TABLE monthly_prices_stats (\n"
            + "   id  bigserial PRIMARY KEY,\n"
            + "   crypto_id INTEGER NOT NULL,\n"
            + "   stats_date DATE NOT NULL,\n"
            + "   oldest_price_record_id BIGINT NOT NULL,\n"
            + "   newest_price_record_id BIGINT NOT NULL,\n"
            + "   min_price_record_id BIGINT NOT NULL,\n"
            + "   max_price_record_id BIGINT NOT NULL\n"
            + ");");
        statement.execute("ALTER TABLE monthly_prices_stats\n"
            + "ADD CONSTRAINT unique_crypto_stats_for_date\n"
            + "UNIQUE(crypto_id, stats_date);");
        statement.execute("INSERT INTO crypto (name, active, statistics_month)\n"
            + "VALUES ('BTC', true, 48);");
    }

    @Test
    public void testHandler() {
        handler.handleDataLoad();
        Collection<CryptoPriceStatistics> statistics = cryptoPriceStatsRepository.getAllStatistics();
        assertNotNull(statistics);
        assertEquals(statistics.size(), 1);
        assertEquals(statistics.iterator().next().getStatisticsDate(), LocalDate.now());
    }
}
