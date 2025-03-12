package com.xkodxdf.app.model.service;

import com.xkodxdf.app.model.dao.CurrencyDaoImpl;
import com.xkodxdf.app.model.entity.CurrencyEntity;

import java.util.List;

public final class CurrencyService {

    private static CurrencyService INSTANCE;

    private final CurrencyDaoImpl currencyDao = CurrencyDaoImpl.getInstance();

    private CurrencyService() {
    }

    public static CurrencyService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CurrencyService();
        }
        return INSTANCE;
    }

    public CurrencyEntity save(String name, String code, String sign) {
        return currencyDao.save(new CurrencyEntity(name, code, sign));
    }

    public CurrencyEntity get(String code) {
        return currencyDao.get(code);
    }

    public CurrencyEntity delete(String code) {
        return currencyDao.delete(code);
    }

    public List<CurrencyEntity> getAll() {
        return currencyDao.getAll();
    }
}
