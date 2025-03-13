package com.xkodxdf.app.model.dao.interfaces;

public interface CurrencyDao<K, V> extends ExchangerDao<K, V> {

    V delete(K key);

}
