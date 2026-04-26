package org.example.Model;

public class Currency {
    private String abbreviation;
    private String name;
    private double exchangeRateToUSD;


    public Currency(String abbreviation, String name, double exchangeRateToUSD){
        this.abbreviation = abbreviation;
        this.name = name;
        this.exchangeRateToUSD = exchangeRateToUSD;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getName() {
        return name;
    }

    public double getExchangeRateToUSD() {
        return exchangeRateToUSD;
    }

    @Override

    public String toString() {
        return name + " (" + abbreviation + ")";
    }


}
