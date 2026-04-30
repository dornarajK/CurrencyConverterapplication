package org.example.application;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.view.CurrencyView;
import org.example.datasource.JPAUtil;

public class Main extends Application {
    // mvn clean compile javafx:run

    @Override
    public void start(Stage stage) {
        try {
            CurrencyView view = new CurrencyView();
            view.init();
            view.start(stage);
        } catch (Throwable t) {
            t.printStackTrace();
            System.err.flush();

            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Startup Error");
            alert.setHeaderText("Application failed to start");
            alert.setContentText(t.toString());
            alert.showAndWait();
            stage.close();
        }
    }

    @Override
    public void stop() {
        JPAUtil.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}