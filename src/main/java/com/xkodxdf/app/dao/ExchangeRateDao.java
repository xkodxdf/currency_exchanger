package com.xkodxdf.app.dao;

import java.util.Optional;

public interface ExchangeRateDao<K, V> extends ExchangerDao<K, V> {

    V update(K key);

    Optional<V> find(K key);
}
