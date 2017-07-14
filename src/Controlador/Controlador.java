package Controlador;

import Modelo.Barra;
import Modelo.Bola;
import Vista.Vista_juego;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;

import java.util.Objects;
import java.util.Random;

public class Controlador {
    private static int dificultad = 3, velocidad = 5;
    private static Bola bola = new Bola();
    private Vista_juego scene;
    private Barra CPUGroup, PlayerGroup;

    Controlador() {
        scene = new Vista_juego();

        CPUGroup = scene.getCPU();
        PlayerGroup = scene.getJugador();

        Main.setScene(scene);

        generarBola();
        movilidadP1();
        configuraciones();
    }

    public static int getDificultad() {
        return dificultad;
    }

    public static int getVelocidad() {
        return velocidad;
    }

    public static double getBola_Y() {
        return bola.getTranslateY();
    }

    private void configuraciones() {
        scene.setOnKeyTyped(e -> {
            if (Objects.equals(e.getCharacter(), "Q") || Objects.equals(e.getCharacter(), "q")) {
                incrementarDificultad();
            }
            if ((Objects.equals(e.getCharacter(), "A")) || (Objects.equals(e.getCharacter(), "a"))) {
                decrementarDificultad();
            }
            if (Objects.equals(e.getCharacter(), "W") || Objects.equals(e.getCharacter(), "w")) {
                velocidad = ++velocidad < 40 ? velocidad : 40;
            }
            if ((Objects.equals(e.getCharacter(), "S")) || (Objects.equals(e.getCharacter(), "s"))) {
                velocidad = --velocidad > 1 ? velocidad : 1;
            }
            if (Objects.equals(e.getCharacter(), "Z") || Objects.equals(e.getCharacter(), "z")) {
                dificultad = 10;
            }
            if ((Objects.equals(e.getCharacter(), "X")) || (Objects.equals(e.getCharacter(), "x"))) {
                velocidad = 5;
            }
            scene.updateText();
        });
    }

    private void movilidadP1() {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP && PlayerGroup.getTranslateY() > 0) {
                PlayerGroup.setDireccion(-0.2);
            } else if (e.getCode() == KeyCode.DOWN && PlayerGroup.getTranslateY() < 480 - 90) {
                PlayerGroup.setDireccion(0.2);
            } else PlayerGroup.setDireccion(0);
        });
        scene.setOnKeyReleased(e -> PlayerGroup.setDireccion(0));
    }

    public void generarBola() {
        try {
            scene.getRootPane().getChildren().remove(bola);
        } catch (Exception ignored) {
        }
        bola = new Bola(PlayerGroup, CPUGroup, this, scene);
        scene.getRootPane().getChildren().add(bola);
        bola.setTranslateX(640 / 2 - 10);
        bola.setTranslateY(480 / 2 - 10);
        Random r = new Random();
        bola.cambiarDireccion(r.nextInt(2));
        bola.cambiarDireccionV(r.nextInt(2));
        bola.setAnguloY(r.nextInt(46) / 100D);
        Task<Void> Bola = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                bola.moverBola();
                return null;
            }
        };
        Thread bola1 = new Thread(Bola);
        bola1.start();
    }

    private void decrementarDificultad() {
        dificultad = dificultad - 1 > 0 ? dificultad - 1 : 1;
    }

    private void incrementarDificultad() {

        dificultad = dificultad + 1 <= 600 ? dificultad + 1 : 600;
    }
}
