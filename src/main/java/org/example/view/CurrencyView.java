package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.example.controller.CurrencyController;
import org.example.entity.Currency;

import java.util.List;
import java.util.Objects;

public class CurrencyView {
    private CurrencyController controller;
    private ComboBox<String> fromCombo;
    private ComboBox<String> toCombo;

    public void init() {
        controller = new CurrencyController();
    }
    public void start(Stage stage) {
        System.out.println("VIEW STARTED");
        List<Currency> currencies = controller.getCurrencies();

        if (currencies == null || currencies.isEmpty()) {
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
        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                amountField.setText(oldValue);
            }
        });

        //  instance variables
        fromCombo = new ComboBox<>();
        toCombo = new ComboBox<>();
        for (Currency c : currencies) {
            String code = c.getAbbreviation();
            fromCombo.getItems().add(code);
            toCombo.getItems().add(code);
        }
        if (!currencies.isEmpty()) {
            fromCombo.setValue(currencies.get(0).getAbbreviation());
            if (currencies.size() > 1)
                toCombo.setValue(currencies.get(1).getAbbreviation());
            else
                toCombo.setValue(currencies.get(0).getAbbreviation());
        }

        Label resultLabel = new Label("Result: ");
        TextField resultField = new TextField();
        resultField.setEditable(false);

        Button addButton = new Button("+ Add Currency");
        addButton.setOnAction(e -> openAddCurrencyDialog(stage));

        Button convertButton = new Button("Convert");
        convertButton.setOnAction(e -> {
            String amount = amountField.getText();
            String from = fromCombo.getValue();
            String to = toCombo.getValue();
            String result = controller.convert(amount, from, to);
            if (result == null) {
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

        // ADD BUTTON ADDED HERE
        VBox root = new VBox(10, addButton, amountLabel, amountField, selectionBox, convertButton, resultLabel, resultBox);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        stage.setTitle("Currency Converter");
        stage.setScene(scene);
        stage.show();
    }

    private void openAddCurrencyDialog(Stage owner) {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.setTitle("Add New Currency");

        // Form fields
        TextField abbrField = new TextField();
        abbrField.setPromptText("Abbreviation (e.g., EUR)");
        TextField nameField = new TextField();
        nameField.setPromptText("Name (e.g., Euro)");
        TextField rateField = new TextField();
        rateField.setPromptText("Exchange rate to USD");

        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        saveButton.setOnAction(e -> {
            String abbr = abbrField.getText().trim().toUpperCase();
            String name = nameField.getText().trim();
            String rateText = rateField.getText().trim();

            if (abbr.isEmpty() || name.isEmpty() || rateText.isEmpty()) {
                showAlert("All fields are required.");
                return;
            }
            double rate;
            try {
                rate = Double.parseDouble(rateText);
            } catch (NumberFormatException ex) {
                showAlert("Rate must be a number.");
                return;
            }

            Currency newCurrency = new Currency(abbr, name, rate);
            boolean success = controller.addCurrency(newCurrency);
            if (success) {
                dialog.close();
                refreshCurrencyOptions();   // update comboboxes in main stage
            } else {
                showAlert("Database error – could not insert currency.");
            }
        });

        cancelButton.setOnAction(e -> dialog.close());

        VBox form = new VBox(10,
                new Label("Abbreviation:"), abbrField,
                new Label("Name:"), nameField,
                new Label("Exchange Rate to USD:"), rateField,
                new HBox(10, saveButton, cancelButton)
        );
        form.setPadding(new Insets(20));
        Scene scene = new Scene(form, 300, 250);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private void refreshCurrencyOptions() {
        List<Currency> currencies = controller.getCurrencies();
        if (currencies == null) return;
        fromCombo.getItems().clear();
        toCombo.getItems().clear();
        for (Currency c : currencies) {
            fromCombo.getItems().add(c.getAbbreviation());
            toCombo.getItems().add(c.getAbbreviation());
        }
        if (!currencies.isEmpty()) {
            fromCombo.setValue(currencies.get(0).getAbbreviation());
            toCombo.setValue(currencies.size() > 1 ? currencies.get(1).getAbbreviation()
                    : currencies.get(0).getAbbreviation());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}