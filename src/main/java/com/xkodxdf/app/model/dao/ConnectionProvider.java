package com.xkodxdf.app.model.dao;

import com.xkodxdf.app.exception.CurrencyExchangerException;
import com.xkodxdf.app.PropertiesUtil;
import com.xkodxdf.app.PropertyKeys;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionProvider {

    private final List<Connection> sourceConnections;
    private final BlockingQueue<Connection> pool;

    public ConnectionProvider() {
        loadDriver();
        this.sourceConnections = new ArrayList<>();
        int poolSize = getPoolSize();
        this.pool = new ArrayBlockingQueue<>(poolSize);
        initConnectionPool(poolSize);
    }

    public Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new CurrencyExchangerException();
        }
    }

    public void closeConnections() throws SQLException {
        for (Connection connection : sourceConnections) {
            connection.close();
        }
    }

    private void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new CurrencyExchangerException(e);
        }
    }

    private void initConnectionPool(int poolSize) {
        for (int i = 0; i < poolSize; i++) {
            Connection connection = open();
            sourceConnections.add(connection);
            Connection proxyConnection = getProxyconnection(connection);
            pool.add(proxyConnection);
        }
    }

    private Connection getProxyconnection(Connection connection) {
        String connectionCloseMethodName = "close";
        return (Connection) Proxy.newProxyInstance(
                ConnectionProvider.class.getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> method.getName().equals(connectionCloseMethodName)
                        ? pool.add((Connection) proxy)
                        : method.invoke(connection, args)
        );
    }

    private int getPoolSize() {
        int defaultPoolSize = 8;
        String poolSizeString = PropertiesUtil.getProperty(PropertyKeys.DB_POOL_SIZE);
        return (poolSizeString != null) ? Integer.parseInt(poolSizeString) : defaultPoolSize;
    }

    private Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.getProperty(PropertyKeys.DB_URL),
                    PropertiesUtil.getProperty(PropertyKeys.DB_USER),
                    PropertiesUtil.getProperty(PropertyKeys.DB_PASSWORD)
            );
        } catch (SQLException e) {
            throw new CurrencyExchangerException(e);
        }
    }
}
