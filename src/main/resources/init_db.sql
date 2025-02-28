CREATE TABLE currencies
(
    id        SERIAL PRIMARY KEY,
    sign      VARCHAR(3)  NOT NULL,
    code      VARCHAR(3)  NOT NULL,
    full_name VARCHAR(48) NOT NULL
);
CREATE UNIQUE INDEX currencies_code_index
    ON currencies (code, full_name);

CREATE TABLE exchange_rates
(
    id                 SERIAL PRIMARY KEY,
    base_currency_id   INTEGER        NOT NULL REFERENCES currencies (id) ON DELETE CASCADE,
    target_currency_id INTEGER        NOT NULL REFERENCES currencies (id) ON DELETE CASCADE,
    rate               DECIMAL(10, 6) NOT NULL

);
CREATE UNIQUE INDEX exchange_rates_base_target_index
    ON exchange_rates (base_currency_id, target_currency_id);

INSERT INTO currencies(sign, code, full_name)
VALUES ('$', 'USD', 'US Dollar'),
       ('₽', 'RUB', 'Russian Ruble'),
       ('€', 'EUR', 'Euro');

INSERT INTO exchange_rates(base_currency_id, target_currency_id, rate)
VALUES ((SELECT id FROM currencies WHERE code = 'RUB'),
        (SELECT id FROM currencies WHERE code = 'USD'),
        87.6967),

       ((SELECT id FROM currencies WHERE code = 'RUB'),
        (SELECT id FROM currencies WHERE code = 'EUR'),
        92.0362),

       ((SELECT id FROM currencies WHERE code = 'USD'),
        (SELECT id FROM currencies WHERE code = 'EUR'),
        0.96);