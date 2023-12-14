CREATE EXTENSION IF NOT EXISTS "pgcrypto";

INSERT INTO cars (id, brand, model, color, year_of_production, price, created, last_updated)
VALUES
  (gen_random_uuid(), 'Toyota', 'Camry', 'Black', 2022, 15999.99, current_timestamp, current_timestamp),
  (gen_random_uuid(), 'Toyota', 'Prius', 'Silver', 2020, 10999.99, current_timestamp, current_timestamp)