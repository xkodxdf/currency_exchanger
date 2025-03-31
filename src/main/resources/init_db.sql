CREATE TABLE currency
(
    id        SERIAL PRIMARY KEY,
    sign      VARCHAR(3)  NOT NULL,
    code      VARCHAR(3)  NOT NULL,
    full_name VARCHAR(48) NOT NULL
);

CREATE UNIQUE INDEX currency_code_index
    ON currency (code);

INSERT INTO currency(sign, code, full_name)
VALUES ('$', 'USD', 'US Dollar'),
       ('₽', 'RUB', 'Russian Ruble'),
       ('€', 'EUR', 'Euro');

CREATE TABLE exchange_rate
(
    id                 SERIAL PRIMARY KEY,
    base_currency_id   INTEGER        NOT NULL REFERENCES currency (id) ON DELETE CASCADE,
    target_currency_id INTEGER        NOT NULL REFERENCES currency (id) ON DELETE CASCADE,
    rate               DECIMAL(12, 6) NOT NULL
);

CREATE UNIQUE INDEX exchange_rate_base_target_index
    ON exchange_rate (base_currency_id, target_currency_id);

INSERT INTO exchange_rate(base_currency_id, target_currency_id, rate)
VALUES ((SELECT id FROM currency WHERE code = 'USD'),
        (SELECT id FROM currency WHERE code = 'RUB'),
        87.6967),

       ((SELECT id FROM currency WHERE code = 'RUB'),
        (SELECT id FROM currency WHERE code = 'USD'),
        0.0116),

       ((SELECT id FROM currency WHERE code = 'EUR'),
        (SELECT id FROM currency WHERE code = 'RUB'),
        93.6639),

       ((SELECT id FROM currency WHERE code = 'USD'),
        (SELECT id FROM currency WHERE code = 'EUR'),
        0.9242);