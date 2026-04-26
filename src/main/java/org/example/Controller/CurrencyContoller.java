package org.example.Controller;



import org.example.Model.Currency;
import org.example.Model.CurrencyList;

import java.util.ArrayList;
import java.util.List;

public class CurrencyContoller {
    private CurrencyList model;

    public CurrencyContoller() {
            model = new CurrencyList();
    }


    public ArrayList<Currency> getCurrencies() {
        return model.getCurrencies();
    }

    public String Convert(String amount, String fromAbbC, String toAbbC){
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
        Currency from = model.findByAbbreviation(fromAbbC);
        Currency to = model.findByAbbreviation(toAbbC);

        // validation for from and to
        if(from == null || to == null) {
            return "Invalid currency selection.";
        }

        // ! conversion
        double result = amountValue / from.getExchangeRateToUSD() * to.getExchangeRateToUSD();


        return String.format("%.2f %s = %.2f %s", amountValue, from.getAbbreviation(), result, to.getAbbreviation());
    }

}
