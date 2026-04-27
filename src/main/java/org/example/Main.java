package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.view.CurrencyView;

public class Main extends Application {
    // mvn javafx:run

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
