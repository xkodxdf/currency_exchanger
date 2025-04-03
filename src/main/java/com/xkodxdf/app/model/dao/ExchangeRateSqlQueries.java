package com.xkodxdf.app.model.dao;

public final class ExchangeRateSqlQueries {

    private ExchangeRateSqlQueries() {
    }

    static final String SAVE = """
            INSERT INTO exchange_rate (base_currency_id, target_currency_id, rate)
            VALUES (
            (SELECT id FROM currency WHERE code = ?),
            (SELECT id FROM currency WHERE code = ?),
            ?);
            """;

    static final String GET = """
            SELECT
                ex_r.id AS exchange_rate_id,
                base_c.id AS base_currency_id,
                base_c.full_name AS base_currency_name,
                base_c.code AS base_currency_code,
                base_c.sign AS base_currency_sign,
                target_c.id AS target_currency_id,
                target_c.full_name AS target_currency_name,
                target_c.code AS target_currency_code,
                target_c.sign AS target_currency_sign,
                ex_r.rate
            FROM exchange_rate AS ex_r
            JOIN currency base_c ON ex_r.base_currency_id = base_c.id
            JOIN currency target_c ON ex_r.target_currency_id = target_c.id
            WHERE base_c.code = ? AND target_c.code = ?;
            """;

    static final String FIND = """
            WITH params AS (SELECT (SELECT id FROM currency WHERE code = ?) AS from_curr_id,
                                                   (SELECT id FROM currency WHERE code = ?) AS to_curr_id),
            
                                 rates AS (SELECT er.id, er.base_currency_id, er.target_currency_id, er.rate, 1 AS priority
                                           FROM exchange_rate er,
                                                params
                                           WHERE er.base_currency_id = params.from_curr_id
                                             AND er.target_currency_id = params.to_curr_id
            
                                           UNION ALL
            
                                           SELECT er.id, er.target_currency_id, er.base_currency_id, (1 / er.rate), 2
                                           FROM exchange_rate er,
                                                params
                                           WHERE er.base_currency_id = params.to_curr_id
                                             AND er.target_currency_id = params.from_curr_id
            
                                           UNION ALL
            
                                           SELECT er1.id,
                                                  er1.base_currency_id,
                                                  er2.target_currency_id,
                                                  er1.rate * er2.rate,
                                                  3
                                           FROM exchange_rate er1
                                                    JOIN exchange_rate er2 ON er1.target_currency_id = er2.base_currency_id
                                                    CROSS JOIN params
                                           WHERE er1.base_currency_id = params.from_curr_id
                                             AND er2.target_currency_id = params.to_curr_id
            
                                           UNION ALL
            
                                           SELECT er2.id,
                                                  er2.base_currency_id,
                                                  er1.target_currency_id,
                                                  1 / (er1.rate * er2.rate),
                                                  4
                                           FROM exchange_rate er1
                                                    JOIN exchange_rate er2 ON er1.base_currency_id = er2.target_currency_id
                                                    CROSS JOIN params
                                           WHERE er1.target_currency_id = params.from_curr_id
                                             AND er2.base_currency_id = params.to_curr_id)
            
                            SELECT r.id AS exchange_rate_id,
                                   c_from.id        AS base_currency_id,
                                   c_from.full_name AS base_currency_name,
                                   c_from.code      AS base_currency_code,
                                   c_from.sign      AS base_currency_sign,
            
                                   c_to.id          AS target_currency_id,
                                   c_to.full_name   AS target_currency_name,
                                   c_to.code        AS target_currency_code,
                                   c_to.sign        AS target_currency_sign,
            
                                   r.rate
            
                            FROM (SELECT * FROM rates ORDER BY priority LIMIT 1) r
                                     JOIN currency c_from ON r.base_currency_id = c_from.id
                                     JOIN currency c_to ON r.target_currency_id = c_to.id;
            """;

    static final String UPDATE = """
            UPDATE exchange_rate
            SET rate = ?
            WHERE base_currency_id = (SELECT id FROM currency WHERE code = ?)
            AND target_currency_id = (SELECT id FROM currency WHERE code = ?);
            """;

    static final String GET_ALL = """
            SELECT
                ex_r.id AS exchange_rate_id,
                base_c.id AS base_currency_id,
                base_c.full_name AS base_currency_name,
                base_c.code AS base_currency_code,
                base_c.sign AS base_currency_sign,
                target_c.id AS target_currency_id,
                target_c.full_name AS target_currency_name,
                target_c.code AS target_currency_code,
                target_c.sign AS target_currency_sign,
                ex_r.rate
            FROM exchange_rate AS ex_r
            JOIN currency base_c ON ex_r.base_currency_id = base_c.id
            JOIN currency target_c ON ex_r.target_currency_id = target_c.id
            ORDER BY base_c.code;
            """;
}
