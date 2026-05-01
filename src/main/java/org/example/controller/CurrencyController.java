package org.example.controller;


import org.example.dao.CurrencyDAO;
import org.example.dao.TransactionDAO;
import org.example.entity.Currency;
import org.example.entity.Transaction;

import java.util.List;

public class CurrencyController {
    private final CurrencyDAO dao = new CurrencyDAO();
    CurrencyDAO currencyDAO;
    private final TransactionDAO transactionDAO = new TransactionDAO();
    public List<Currency> getCurrencies() {
        try {
            return dao.getAllCurrencies();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String convert(String amountStr, String fromAbbr, String toAbbr) {
        try {
            double amount = Double.parseDouble(amountStr);
            double fromRate = dao.getExchangeRate(fromAbbr);
            double toRate = dao.getExchangeRate(toAbbr);
            double result = (amount / fromRate) * toRate;
            String resultStr = String.format("%.2f", result);

            // Store transaction
            Currency source = dao.findByAbbreviation(fromAbbr);
            Currency target = dao.findByAbbreviation(toAbbr);
            Transaction transaction = new Transaction(source, target, amount, result);
            transactionDAO.save(transaction);

            return resultStr;
        } catch (NumberFormatException e) {
            return "Invalid amount";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addCurrency(Currency currency) {
        try {
            dao.insert(currency);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}