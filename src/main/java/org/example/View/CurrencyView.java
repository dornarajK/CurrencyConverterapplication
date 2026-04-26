package org.example.LayoutsbuildingtheUI_6_2.View;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.LayoutsbuildingtheUI_6_2.Controller.CurrencyContoller;
import org.example.LayoutsbuildingtheUI_6_2.Model.Currency;

import java.awt.*;

import javafx.scene.control.Label;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import javafx.geometry.Insets;
public class CurrencyView {
    private CurrencyContoller controller;


    public void init(){
        controller = new CurrencyContoller();
    }


    public void start(Stage stage) {
        System.out.println("VIEW STARTED");
        // imput Amaunt
        Label amauntLable = new Label("Amaunt: ");
        TextField  amauntField = new TextField();

        // falidation listen typing. check if input is valid value
        amauntField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                amauntField.setText(oldValue);
            }
        });

        // --currency Dropdown box--

        // box for old Vale
        Label formCurrencyLabel = new Label("From: ");
        ComboBox<String> fromCurrencyComboBox = new ComboBox<>();

        // box for new value
        Label toLable = new Label("To: ");
        ComboBox<String> toCurrencyComboBox = new ComboBox<>();

        // Get data from controller
        List<Currency> list = controller.getCurrencies();
        System.out.println(list);
        // for-each loop to add currencies to dropdown box
        for(Currency c : list){
            String code = c.getAbbreviation();
            fromCurrencyComboBox.getItems().add(code);
            toCurrencyComboBox.getItems().add(code);
        }

        // set a defult selection
        fromCurrencyComboBox.setValue(list.get(0).getAbbreviation());
        toCurrencyComboBox.setValue(list.get(1).getAbbreviation());

        // Result box
        Label resultLable = new Label ("Result: ");
        TextField resultField = new TextField();
        resultField.setEditable(false);

        // Button for convert vale and show result
        Button convertButton = new Button("Convert");
        convertButton.setOnAction(e -> {
            String amount = amauntField.getText();
            String from = fromCurrencyComboBox.getValue();
            String to = toCurrencyComboBox.getValue();

            String result = controller.Convert(amount, from, to);
            resultField.setText(result);

        });

        Label instructionLabel = new Label("Enter amount and select currencies to convert.");

        // View
        VBox formBox_ = new VBox(fromCurrencyComboBox);
        VBox toBox_ = new VBox(toCurrencyComboBox);
        HBox selectionBox = new HBox(10, formBox_, toBox_);
        HBox resultBox = new HBox(resultField);

        VBox root = new VBox(amauntField, selectionBox, convertButton, resultBox);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        stage.setTitle("Currency Converter");
        stage.setScene(scene);
        stage.show();


    }

}
