package Controlador;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Pong 3D");
        stage.setResizable(false);
        new Controller();
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    static void setScene(Scene scene) {
        stage.setScene(scene);
    }
}
