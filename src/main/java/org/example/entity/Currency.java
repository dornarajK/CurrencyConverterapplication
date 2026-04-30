package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "currency")
public class Currency {

    @Id
    private String abbreviation;

    @Column(name = "name")
    private String name;

    @Column(name = "rate")
    private double exchangeRateToUSD;

    public Currency() {}
    public Currency(String abbreviation, String name, double exchangeRateToUSD) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.exchangeRateToUSD = exchangeRateToUSD;
    }


    public String getAbbreviation() { return abbreviation; }
    public void setAbbreviation(String abbreviation) { this.abbreviation = abbreviation; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getExchangeRateToUSD() { return exchangeRateToUSD; }
    public void setExchangeRateToUSD(double exchangeRateToUSD) { this.exchangeRateToUSD = exchangeRateToUSD; }
}