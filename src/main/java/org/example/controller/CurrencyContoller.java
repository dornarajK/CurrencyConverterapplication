package org.example.controller;

import org.example.entity.Currency;
import org.example.dao.CurrencyDAO;

import java.sql.SQLException;
import java.util.List;

public class CurrencyContoller {
    private final CurrencyDAO currencyDAO = new CurrencyDAO();
    private List<Currency> currencies;

    public CurrencyContoller() {
            loadCurrencies();
    }


    public void loadCurrencies() {
        try {
            currencies = currencyDAO.getAllCurrencies();
        } catch (SQLException e) {
            currencies = null;
            e.printStackTrace();
        }
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }



    public String Convert(String amount, String fromAbb, String toAbb) {
        // validation
        if(amount == null || amount.isEmpty()) {
            return "Amount is required.";
        }
        double amountValue;

        try {
            // strign to number
            amountValue = Double.parseDouble(amount);

        } catch (NumberFormatException e) {
            return "Invalid amount format.";
        }

        // number amaunt value validation
        if(amountValue < 0) {
            return "Amount must be positive.";
        }

        // find value form and to
        double from, to;
        try {
            from = currencyDAO.getExchangeRate(fromAbb);
            to = currencyDAO.getExchangeRate(toAbb);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


        // validation for from and to
        if(from < 0 || to < 0) {
            return "Invalid currency selection.";
        }

        // ! conversion
        double result = amountValue / from * to;
        return String.format("%.2f %s = %.2f %s", amountValue, fromAbb, result, toAbb);



    }

}
