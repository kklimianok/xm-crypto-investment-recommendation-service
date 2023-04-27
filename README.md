# Crypto Investment Recommendation Service.

Project structure:
- sql - Contains SQL tables structure creation and data example scripts. To make service able to operate, it's necessary to run sql/structure.sql on PostgreSQL. This will create required data structure. sql/data.sql inserts all current Cryptos into DB. Inserted cryptos enable its support. 'active' value specify if Crypto is enabled or not, 'statistics_month' specify the date range which require to collect price statistics. It's 1 month by default, but for provided data there are rather old dates specified, so for example data, this range is increased to 48 months.
- cirs-domain - Basic domains for the project
- cirs-repositroy - Repositories used among other modules
- cirs-dataloader - Data Loading module. Provides a separate application that triggers data loading by the Scheduler. Data loading is triggered every 10 minutes and read CSV files. If there are new prices, statistics for required date range (based on statistics_month from crypto) recalculation will be initiated. It also initiated every 24 hours, so we gate collected statistics every day for a month (for specified date range).
- cirs-rest - REST module that includes following submodules:
  - cirs-rest-dto - DTOs that are used for operating with API
  - cirs-rest-service - Services used by API resources
  - cirs-rest-api - Resources implementation and REST API application

## BUILD
mvn clean install runs the application build with tests.

## RUN
The service consists of 2 applications: Dataloader, that is a usual Spring application and responsible for data loading and CryptoInvestmentRecommendation that is a Sping Web application and provides REST API for the service.
Each application include application.properties for configuration. It includes JPA configuration. 
- For Dataloader there is also *csvDirectoryPath* property that specify the CSV files directory path
- For CryptoInvestmentRecommendation there is also *ipRateLimitMillis* property that specify IP limit for time in milliseconds. If value is not specified, IP Rate limit is disabled
- src/main/resources/hibernate/hibernate.cfg.xml define Hibernate's configuration

