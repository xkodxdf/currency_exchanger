$(document).ready(function () {
    const host = "http://localhost:8080/exchanger"

    function requestCurrencies() {
        $.ajax({
            url: `${host}/currencies`,
            type: "GET",
            dataType: "json",
            success: function (data) {
                const tbody = $('.currencies-table tbody');
                tbody.empty();
                $.each(data, function (index, currency) {
                    const row = $('<tr></tr>');
                    row.append($('<td></td>').text(currency.code));
                    row.append($('<td></td>').text(currency.name));
                    row.append($('<td></td>').text(currency.sign));
                    //delete button
                    const deleteButton = $('<button></button>')
                        .text('🇽')
                        .addClass('btn btn-sm delete-currency')
                        .attr('data-code', currency.code);
                    row.append($('<td></td>').append(deleteButton));
                    tbody.append(row);
                });

                $('.delete-currency').click(function () {
                    const currencyCode = $(this).data('code');
                    if (confirm(`Are you sure you want to delete currency ${currencyCode}?`)) {
                        $.ajax({
                            url: `${host}/currency/${currencyCode}`,
                            type: 'DELETE',
                            success: function (response) {
                                requestCurrencies();
                                requestExchangeRates();
                            },
                            error: function (jqXHR, textStatus, errorThrown) {
                                const error = JSON.parse(jqXHR.responseText);
                                const toast = $('#api-error-toast');
                                $(toast).find('.toast-body').text(error.message);
                                toast.toast("show");
                            }
                        });
                    }
                });

                const newRateBaseCurrency = $("#new-rate-base-currency");
                newRateBaseCurrency.empty();
                newRateBaseCurrency.append('<option value="" disabled selected>Select base currency</option>'); // Плейсхолдер

                $.each(data, function (index, currency) {
                    newRateBaseCurrency.append(`<option value="${currency.code}">${currency.code}</option>`);
                });

                const newRateTargetCurrency = $("#new-rate-target-currency");
                newRateTargetCurrency.empty();
                newRateTargetCurrency.append('<option value="" disabled selected>Select target currency</option>'); // Плейсхолдер

                $.each(data, function (index, currency) {
                    newRateTargetCurrency.append(`<option value="${currency.code}">${currency.code}</option>`);
                });

                const convertBaseCurrency = $("#convert-base-currency");
                convertBaseCurrency.empty();
                convertBaseCurrency.append('<option value="" disabled selected>Select base currency</option>'); // Плейсхолдер

                $.each(data, function (index, currency) {
                    convertBaseCurrency.append(`<option value="${currency.code}">${currency.code}</option>`);
                });

                const convertTargetCurrency = $("#convert-target-currency");
                convertTargetCurrency.empty();
                convertTargetCurrency.append('<option value="" disabled selected>Select target currency</option>'); // Плейсхолдер

                $.each(data, function (index, currency) {
                    convertTargetCurrency.append(`<option value="${currency.code}">${currency.code}</option>`);
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                const error = JSON.parse(jqXHR.responseText);
                const toast = $('#api-error-toast');

                $(toast).find('.toast-body').text(error.message);
                toast.toast("show");
            }
        });
    }

    requestCurrencies();

    $("#add-currency").submit(function (e) {
        e.preventDefault();

        $.ajax({
            url: `${host}/currencies`,
            type: "POST",
            data: $("#add-currency").serialize(),
            success: function (data) {
                requestCurrencies();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                const error = JSON.parse(jqXHR.responseText);
                const toast = $('#api-error-toast');

                $(toast).find('.toast-body').text(error.message);
                toast.toast("show");
            }
        });

        return false;
    });

    function requestExchangeRates() {
        $.ajax({
            url: `${host}/exchangeRates`,
            type: "GET",
            dataType: "json",
            success: function (response) {
                const tbody = $('.exchange-rates-table tbody');
                tbody.empty();
                $.each(response, function (index, rate) {
                    const row = $('<tr></tr>');
                    const currency = rate.baseCurrency.code + rate.targetCurrency.code;
                    const exchangeRate = rate.rate;
                    row.append($('<td></td>').text(currency));
                    row.append($('<td></td>').text(exchangeRate));
                    row.append($('<td></td>').html(
                        '<button class="btn btn-secondary btn-sm exchange-rate-edit"' +
                        'data-bs-toggle="modal" data-bs-target="#edit-exchange-rate-modal">Edit</button>'
                    ));
                    tbody.append(row);
                });
            },
            error: function () {
                const error = JSON.parse(jqXHR.responseText);
                const toast = $('#api-error-toast');

                $(toast).find('.toast-body').text(error.message);
                toast.toast("show");
            }
        });
    }

    requestExchangeRates();

    $(document).delegate('.exchange-rate-edit', 'click', function () {
        const pair = $(this).closest('tr').find('td:first').text();
        const exchangeRate = $(this).closest('tr').find('td:eq(1)').text();

        // insert values into the modal
        $('#edit-exchange-rate-modal .modal-title').text(`Edit ${pair} Exchange Rate`);
        $('#edit-exchange-rate-modal #exchange-rate-input').val(exchangeRate);
    });

    $('#edit-exchange-rate-modal .btn-primary').click(function () {
        const pair = $('#edit-exchange-rate-modal .modal-title').text().replace('Edit ', '').replace(' Exchange Rate', '');
        const exchangeRate = $('#edit-exchange-rate-modal #exchange-rate-input').val();

        const row = $(`tr:contains(${pair})`);
        row.find('td:eq(1)').text(exchangeRate);

        $.ajax({
            url: `${host}/exchangeRate/${pair}`,
            type: "PATCH",
            contentType: "application/x-www-form-urlencoded",
            data: `rate=${exchangeRate}`,
            success: function () {

            },
            error: function (jqXHR, textStatus, errorThrown) {
                const error = JSON.parse(jqXHR.responseText);
                const toast = $('#api-error-toast');

                $(toast).find('.toast-body').text(error.message);
                toast.toast("show");
            }
        });

        $('#edit-exchange-rate-modal').modal('hide');
    });

    $(document).ready(function () {
        $('#swap-currencies').click(function () {
            const baseCurrency = $('#convert-base-currency').val();
            const targetCurrency = $('#convert-target-currency').val();
            $('#convert-base-currency').val(targetCurrency);
            $('#convert-target-currency').val(baseCurrency);
        });

        $('#swap-new-rate-currencies').click(function () {
            const baseCurrency = $('#new-rate-base-currency').val();
            const targetCurrency = $('#new-rate-target-currency').val();
            $('#new-rate-base-currency').val(targetCurrency);
            $('#new-rate-target-currency').val(baseCurrency);
        });
    });

    $(document).ready(function () {
        // Функция для проверки одинаковых валют
        function validateCurrencySelection(baseSelector, targetSelector, errorMessage) {
            const baseCurrency = $(baseSelector).val();
            const targetCurrency = $(targetSelector).val();

            if (baseCurrency && targetCurrency && baseCurrency === targetCurrency) {
                alert(errorMessage);
                $(targetSelector).val(''); // Сбрасываем выбор целевой валюты
                return false;
            }
            return true;
        }

        $('#new-rate-base-currency, #new-rate-target-currency').change(function () {
            validateCurrencySelection(
                '#new-rate-base-currency',
                '#new-rate-target-currency',
                'Base and target currencies cannot be the same!'
            );
        });

        // Обработчик для выпадающих списков в блоке конвертера
        $('#convert-base-currency, #convert-target-currency').change(function () {
            validateCurrencySelection(
                '#convert-base-currency',
                '#convert-target-currency',
                'Base and target currencies cannot be the same!'
            );
        });
    });

    $("#add-exchange-rate").submit(function (e) {
        e.preventDefault();
        $.ajax({
            url: `${host}/exchangeRates`,
            type: "POST",
            data: $("#add-exchange-rate").serialize(),
            success: function (data) {
                requestExchangeRates();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                const error = JSON.parse(jqXHR.responseText);
                const toast = $('#api-error-toast');

                $(toast).find('.toast-body').text(error.message);
                toast.toast("show");
            }
        });

        return false;
    });


    $("#convert").submit(function (e) {
        e.preventDefault();
        const baseCurrency = $("#convert-base-currency").val();
        const targetCurrency = $("#convert-target-currency").val();
        const amount = $("#convert-amount").val();

        $.ajax({
            url: `${host}/exchange?from=${baseCurrency}&to=${targetCurrency}&amount=${amount}`,
            type: "GET",
            success: function (data) {
                $("#convert-converted-amount").val(data.convertedAmount);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                const error = JSON.parse(jqXHR.responseText);
                const toast = $('#api-error-toast');

                $(toast).find('.toast-body').text(error.message);
                toast.toast("show");
            }
        });

        return false;
    });
});
