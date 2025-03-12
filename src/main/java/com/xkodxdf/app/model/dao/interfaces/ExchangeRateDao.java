package com.xkodxdf.app.model.dao.interfaces;

public interface ExchangeRateDao<K, V> extends ExchangerDao<K, V> {

    V save(K key);

    V update(K key);
}
