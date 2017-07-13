package Views;

import Controlador.Controller;
import Model.Barra;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Vista_juego extends Scene {

    private Barra Jugador;
    private Barra CPU;
    private Text PuntajeP1 = new Text("0"), PuntajeP2 = new Text("0"), config;
    private static Pane root;

    public Vista_juego() {
        super(getPaneRoot(), 640, 480, true);
        Circle circle = new Circle(10, Color.WHITE);
        setCamera(new PerspectiveCamera());
        root.setStyle("-fx-background-color: Black");
        Rectangle midline = new Rectangle(5, 500);
        config = new Text(" Q : Incrementar Dificultad. " + Controller.getDificultad() + "\n A : Decrecer Dificultad.\n Z: Dificultad por defecto.\n W : Aumentar Velocidad. " + Controller.getVelocidad() + "\n A : Disminuir Velocidad.\n X : Velocidad por defecto.");
        root.getChildren().addAll(circle, midline, PuntajeP1, PuntajeP2, config);
        circle.setTranslateX(640 / 2);
        circle.setTranslateY(480 / 2);
        config.setTranslateX(325);
        config.setTranslateY(415);
        config.setFill(Color.WHITE);
        PuntajeP1.setTranslateX(640 / 4);
        PuntajeP2.setTranslateX(640 / 4 * 3);
        PuntajeP1.setTranslateY(480 / 8);
        PuntajeP2.setTranslateY(480 / 8);
        PuntajeP1.setFill(Color.WHITE);
        PuntajeP2.setFill(Color.WHITE);
        PuntajeP1.setFont(new Font("CONSOLAS", 60));
        PuntajeP2.setFont(new Font("CONSOLAS", 60));
        midline.setX(640 / 2 - 2);
        midline.setFill(Color.WHITE);
        Jugador = new Barra();
        Jugador.setTranslateX(30);
        Jugador.setTranslateY(195);
        CPU = new Barra();
        CPU.setTranslateX(580);
        CPU.setTranslateY(195);
        root.getChildren().addAll(Jugador, CPU);
    }


    private static Pane getPaneRoot() {
        root = new Pane();
        return root;
    }

    public Barra getJugador() {
        return Jugador;
    }

    public Barra getCPU() {
        return CPU;
    }

    public void addScoreP1() {
        PuntajeP1.setText((Integer.parseInt(PuntajeP1.getText()) + 1) + "");
    }

    public void addScoreP2() {
        PuntajeP2.setText((Integer.parseInt(PuntajeP2.getText()) + 1) + "");
    }

    public Pane getRootPane() {
        return root;
    }

    public void updateText() {
        config.setText(" Q : Incrementar Dificultad. " + Controller.getDificultad() + "\n A : Decrecer Dificultad.\n Z: Dificultad por defecto.\n W : Aumentar Velocidad. " + Controller.getVelocidad() + "\n A : Disminuir Velocidad.\n X : Velocidad por defecto.");
    }
}
