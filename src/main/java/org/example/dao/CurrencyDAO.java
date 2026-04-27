package org.example.dao;

import org.example.datasource.MariaDBConnection;
import org.example.entity.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO {


    public List<Currency> getAllCurrencies() throws SQLException {
        List<Currency> currencies = new ArrayList<>();
        String sql = "SELECT abbreviation, name, rate FROM currency";
        try (Connection conn = MariaDBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                currencies.add(new Currency(
                        rs.getString("abbreviation"),
                        rs.getString("name"),
                        rs.getDouble("rate")
                ));
            }
        }
        return currencies;
    }


    public double getExchangeRate(String abbreviation) throws SQLException {
        String sql = "SELECT rate FROM currency WHERE abbreviation = ?";
        try (Connection conn = MariaDBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, abbreviation);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("rate");
                } else {
                    throw new SQLException("Currency not found: " + abbreviation);
                }
            }
        }
    }
}