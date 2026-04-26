package org.example.View;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Controller.CurrencyContoller;
import org.example.Model.Currency;

import java.util.List;
import java.util.Objects;

public class CurrencyView {

    private CurrencyContoller controller;

    public void init() {
        controller = new CurrencyContoller();
    }

    public void start(Stage stage) {
        System.out.println("VIEW STARTED");

        // Input amount
        Label amountLabel = new Label("Amount: ");
        TextField amountField = new TextField();

        // Validation: only numbers and decimal point
        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                amountField.setText(oldValue);
            }
        });

        // Currency dropdowns
        Label fromCurrencyLabel = new Label("From: ");
        ComboBox<String> fromCurrencyComboBox = new ComboBox<>();

        Label toLabel = new Label("To: ");
        ComboBox<String> toCurrencyComboBox = new ComboBox<>();

        // Load currencies from controller
        List<Currency> list = controller.getCurrencies();
        System.out.println(list);
        for (Currency c : list) {
            String code = c.getAbbreviation();
            fromCurrencyComboBox.getItems().add(code);
            toCurrencyComboBox.getItems().add(code);
        }

        // Set default selections
        if (!list.isEmpty()) {
            fromCurrencyComboBox.setValue(list.get(0).getAbbreviation());
            if (list.size() > 1) {
                toCurrencyComboBox.setValue(list.get(1).getAbbreviation());
            } else {
                toCurrencyComboBox.setValue(list.get(0).getAbbreviation());
            }
        }

        // Result field (read‑only)
        Label resultLabel = new Label("Result: ");
        TextField resultField = new TextField();
        resultField.setEditable(false);

        // Convert button
        Button convertButton = new Button("Convert");
        convertButton.setOnAction(e -> {
            String amount = amountField.getText();
            String from = fromCurrencyComboBox.getValue();
            String to = toCurrencyComboBox.getValue();

            String result = controller.Convert(amount, from, to);
            resultField.setText(result);
        });

        // Layout
        VBox formBox = new VBox(fromCurrencyComboBox);
        VBox toBox = new VBox(toCurrencyComboBox);
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