# HHH-16775

## Simple test of the issue
https://hibernate.atlassian.net/browse/HHH-16775


## Prerequisities

For running the application you need to have installed:

- Java 17
- Kotlin 1.8.21
- Docker
- Docker Compose

## Running the application

1. Start the database

```bash
docker-compose up -d
```

2. Run the test `BulkInsertTest` from your favorite IDE

3. Results

- Test will crash during the first commit as is with value `useBulkCopyForBatchInsert=true;` in the URL in the definition in the companion object `BulkInsertTest.kt`
- Without this value the test will pass and logs will show that data were inserted in batches and then cleaned up
