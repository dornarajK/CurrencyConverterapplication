package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.View.CurrencyView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        CurrencyView view = new CurrencyView();
        view.init();
        view.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
