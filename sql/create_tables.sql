CREATE TABLE IF NOT EXISTS cars (
    id VARCHAR(255) PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL,
    year_of_production INTEGER NOT NULL,
    price REAL NOT NULL,
    created TIMESTAMP NOT NULL,
    last_updated TIMESTAMP NOT NULL
);