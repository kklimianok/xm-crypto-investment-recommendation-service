CREATE TABLE crypto (
   id SERIAL PRIMARY KEY,
   name TEXT NOT NULL,
   active BOOL NOT NULL DEFAULT true,
   statistics_month SMALLINT NOT NULL DEFAULT 1
);

ALTER TABLE crypto
ADD CONSTRAINT unique_crypto_name
UNIQUE(name);

CREATE TABLE crypto_price_record (
   id  bigserial PRIMARY KEY,
   crypto_id INTEGER NOT NULL,
   timestamp TIMESTAMP NOT NULL,
   price REAL NOT NULL
);

ALTER TABLE crypto_price_record
ADD CONSTRAINT unique_crypto_price_record_for_time
UNIQUE(crypto_id, timestamp);

CREATE TABLE monthly_prices_stats (
   id  bigserial PRIMARY KEY,
   crypto_id INTEGER NOT NULL,
   stats_date DATE NOT NULL,
   oldest_price_record_id BIGINT NOT NULL,
   newest_price_record_id BIGINT NOT NULL,
   min_price_record_id BIGINT NOT NULL,
   max_price_record_id BIGINT NOT NULL
);

ALTER TABLE monthly_prices_stats
ADD CONSTRAINT unique_crypto_stats_for_date
UNIQUE(crypto_id, stats_date);