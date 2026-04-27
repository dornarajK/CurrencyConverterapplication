package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.CurrencyContoller;
import org.example.entity.Currency;

import java.util.List;
import java.util.Objects;

public class CurrencyView {
    private CurrencyContoller controller;

    public void init() {
        controller = new CurrencyContoller();
    }

    public void start(Stage stage) {
        System.out.println("VIEW STARTED");
        List<Currency> currencies = controller.getCurrencies();

        if (currencies == null || currencies.isEmpty()) {
            // Show error and exit gracefully

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database error");
            alert.setHeaderText("Cannot load currency data");
            alert.setContentText("Please check your database connection.\nMake sure MariaDB is running and the 'currencydb' exists with a 'currencies' table.");
            alert.showAndWait();
            stage.close();

            return;
        }

        // Input amount
        Label amountLabel = new Label("Amount: ");
        TextField amountField = new TextField();

        // Validation: only numbers and decimal point
        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                amountField.setText(oldValue);
            }
        });


        ComboBox<String> fromCombo = new ComboBox<>();
        ComboBox<String> toCombo = new ComboBox<>();
        for (Currency c : currencies) {
            String code = c.getAbbreviation();
            fromCombo.getItems().add(code);
            toCombo.getItems().add(code);
        }
        // Set default selections
        if (!currencies.isEmpty()) {
            fromCombo.setValue(currencies.get(0).getAbbreviation());
            if (currencies.size() > 1)
                toCombo.setValue(currencies.get(1).getAbbreviation());
            else
                toCombo.setValue(currencies.get(0).getAbbreviation());
        }

        // Result field (read‑only)
        Label resultLabel = new Label("Result: ");
        TextField resultField = new TextField();
        resultField.setEditable(false);

        // Convert button
        Button convertButton = new Button("Convert");
        convertButton.setOnAction(e -> {
            String amount = amountField.getText();
            String from = fromCombo.getValue();
            String to = toCombo.getValue();

            String result = controller.Convert(amount, from, to);

            if (result == null) {
                // Database error during conversion
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database error");
                alert.setHeaderText("Could not fetch exchange rate");
                alert.setContentText("Please check your database connection and try again.");
                alert.showAndWait();
                resultField.clear();
            } else {
                resultField.setText(result);
            }
        });



        // Layout
        VBox formBox = new VBox(fromCombo);
        VBox toBox = new VBox(toCombo);
        HBox selectionBox = new HBox(10, formBox, toBox);
        HBox resultBox = new HBox(resultField);

        VBox root = new VBox(10, amountLabel, amountField, selectionBox, convertButton, resultLabel, resultBox);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 300);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        stage.setTitle("Currency Converter");
        stage.setScene(scene);
        stage.show();
    }
}