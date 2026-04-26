package org.example.Model;

import java.util.ArrayList;

public class CurrencyList {
    ArrayList<Currency> currencies;

    public CurrencyList() {
        currencies = new ArrayList<>();
        // add some sample currencies
        currencies.add(new Currency("USD", "US Dollar", 1.0));
        currencies.add(new Currency("EUR", "Euro", 0.85));
        currencies.add(new Currency("JPY", "Japanese Yen", 110.0));
        currencies.add(new Currency("GBP", "British Pound", 0.75));

    }

    public ArrayList<Currency> getCurrencies() {
        return currencies;
    }

    public Currency findByAbbreviation(String Abbreviation){
        return currencies.stream()
                .filter(c -> c.getAbbreviation().equals(Abbreviation))
                .findFirst()
                .orElse(null);

    }

}
