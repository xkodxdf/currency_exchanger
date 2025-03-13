package com.xkodxdf.app.model.dao.interfaces;

public interface ExchangeRateDao<K, V> extends ExchangerDao<K, V> {

    V update(K key);
}
