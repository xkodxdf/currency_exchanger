package com.xkodxdf.app.service;

import com.xkodxdf.app.ErrorMessage;
import com.xkodxdf.app.dto.ExchangeRateRequestDto;
import com.xkodxdf.app.dto.ExchangeRequestDto;
import com.xkodxdf.app.dto.ExchangeResponseDto;
import com.xkodxdf.app.exception.DataNotFoundException;
import com.xkodxdf.app.model.dao.interfaces.ExchangeRateDao;
import com.xkodxdf.app.model.entity.ExchangeEntity;
import com.xkodxdf.app.model.entity.ExchangeRateEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class ExchangeService extends BaseService {

    private final ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> exchangeRateDao;

    public ExchangeService(ExchangeRateDao<ExchangeRateRequestDto, ExchangeRateEntity> exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    public ExchangeResponseDto getExchangeResponseDto(ExchangeRequestDto requestDtoToExchange) {
        requestDtoValidator.validateExchangeRequestDto(requestDtoToExchange);
        ExchangeRateEntity exchangeRate = findExchangeRate(requestDtoToExchange.exchangeRateRequestDto());
        BigDecimal amountToExchange = new BigDecimal(requestDtoToExchange.amount());
        ExchangeEntity exchangeEntity = new ExchangeEntity(exchangeRate, amountToExchange);
        return new ExchangeResponseDto(exchangeEntity);
    }

    private ExchangeRateEntity findExchangeRate(ExchangeRateRequestDto requestDto) {
        return findDirectExchangeRate(requestDto)
                .or(() -> findReversedExchangeRate(requestDto))
                .orElseThrow(() -> new DataNotFoundException(ErrorMessage.NOT_FOUND_ERR));
    }

    private Optional<ExchangeRateEntity> findDirectExchangeRate(ExchangeRateRequestDto requestDto) {
        try {
            return Optional.of(exchangeRateDao.get(requestDto));
        } catch (DataNotFoundException e) {
            return Optional.empty();
        }
    }

    private Optional<ExchangeRateEntity> findReversedExchangeRate(ExchangeRateRequestDto requestDto) {
        try {
            ExchangeRateRequestDto invertedRequestDto = createInvertedExchangeRateRequestDto(requestDto);
            ExchangeRateEntity exchangeRate = exchangeRateDao.get(createInvertedExchangeRateRequestDto(invertedRequestDto));
            return Optional.of(createInvertedExchangeRate(exchangeRate));
        } catch (DataNotFoundException e) {
            return Optional.empty();
        }
    }

    private ExchangeRateRequestDto createInvertedExchangeRateRequestDto(ExchangeRateRequestDto requestDto) {
        return new ExchangeRateRequestDto(
                requestDto.targetCurrencyCode(),
                requestDto.baseCurrencyCode()
        );
    }

    private ExchangeRateEntity createInvertedExchangeRate(ExchangeRateEntity exchangeRate) {
        int scale = 4;
        long idForNotPresentedRates = -1L;
        BigDecimal invertedRate = BigDecimal.ONE.divide(exchangeRate.rate(), scale, RoundingMode.HALF_EVEN);
        return new ExchangeRateEntity(
                idForNotPresentedRates,
                exchangeRate.targetCurrency(),
                exchangeRate.baseCurrency(),
                invertedRate
        );
    }
}
