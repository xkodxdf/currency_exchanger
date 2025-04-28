package com.xkodxdf.app.dao;

public interface CurrencyDao<K, V> extends ExchangerDao<K, V> {

    V delete(K key);

}
