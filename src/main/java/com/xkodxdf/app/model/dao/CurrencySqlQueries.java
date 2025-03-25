package com.xkodxdf.app.model.dao;

public final class CurrencySqlQueries {

    private CurrencySqlQueries() {
    }

    static final String SAVE = """
            INSERT INTO currency (sign, code, full_name)
            VALUES (?, ?, ?);""";

    static final String GET = """
            SELECT id, sign, code, full_name
            FROM currency
            WHERE code = ?;""";

    static final String DELETE = """
            DELETE FROM currency
            WHERE code = ?
            RETURNING id, sign, code, full_name;
            """;

    static final String GET_ALL = """
            SELECT id, sign, code, full_name
            FROM currency
            ORDER BY code;
            """;
}
