package Controlador;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static Modelo.Audio.delayFix;

public class Main extends Application {

    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    static void setScene(Scene scene) {

        stage.setScene(scene);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        delayFix();
        stage = primaryStage;
        stage.setTitle("Pong 3D");
        stage.getIcons().add(new Image("Imagen/pong.png"));
        stage.setResizable(false);
        new Controlador();
        stage.show();
    }
}
