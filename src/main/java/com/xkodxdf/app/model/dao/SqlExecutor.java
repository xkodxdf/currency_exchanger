package com.xkodxdf.app.model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlExecutor<T> {

    T execute(PreparedStatement preparedStatement) throws SQLException;
}
