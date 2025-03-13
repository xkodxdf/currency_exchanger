package com.xkodxdf.app.model.dao.interfaces;

import java.util.List;

public interface ExchangerDao<K, V> {

    V save(K key);

    V get(K key);

    List<V> getAll();
}
