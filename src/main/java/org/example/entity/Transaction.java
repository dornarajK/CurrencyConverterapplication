package org.example.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_currency_abbr", nullable = false)
    private Currency sourceCurrency;

    @ManyToOne
    @JoinColumn(name = "target_currency_abbr", nullable = false)
    private Currency targetCurrency;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private double convertedAmount;

    @Column(nullable = false)
    private LocalDateTime timestamp;


    public Transaction() {}

    public Transaction(Currency source, Currency target, double amount, double convertedAmount) {
        this.sourceCurrency = source;
        this.targetCurrency = target;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
        this.timestamp = LocalDateTime.now();
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Currency getSourceCurrency() { return sourceCurrency; }
    public void setSourceCurrency(Currency sourceCurrency) { this.sourceCurrency = sourceCurrency; }

    public Currency getTargetCurrency() { return targetCurrency; }
    public void setTargetCurrency(Currency targetCurrency) { this.targetCurrency = targetCurrency; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public double getConvertedAmount() { return convertedAmount; }
    public void setConvertedAmount(double convertedAmount) { this.convertedAmount = convertedAmount; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}