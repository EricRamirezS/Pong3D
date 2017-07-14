package Controlador;

import Modelo.Audio;
import Modelo.Barra;
import Modelo.Bola;
import Vista.Vista_juego;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.Objects;
import java.util.Random;

public class Contolador {
    private static int dificultad = 7, velocidad = 5;
    private static Bola bola = new Bola();
    private Vista_juego scene;
    private Barra CPUGroup, PlayerGroup;

    Contolador() {
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

    private void generarBola() {
        try {
            scene.getRootPane().getChildren().remove(bola);
        } catch (Exception ignored) {
        }
        bola = new Bola();
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
                moverBola();
                return null;
            }
        };
        Thread bola1 = new Thread(Bola);
        bola1.start();
    }

    private boolean coalisionBola() {
        double bola_X = bola.getTranslateX();
        double bola_Y = bola.getTranslateY();
        double bola_AY = bola.getAnguloY();
        if (bola_X > 15 && bola_X <= 60) {
            if (bola_Y + 20 > PlayerGroup.getTranslateY() && bola_Y < PlayerGroup.getTranslateY() + 90) {
                if (bola.canIncreaseSpeed()) {
                    bola.cambiarDireccion(0);
                    bola.incrementarVelocidad();
                    bola.setCanIncreaseSpeed(false);
                    double angulo = (bola_Y + 20 - PlayerGroup.getTranslateY() - 55) / 1000;
                    bola.setAnguloY(bola_AY + (PlayerGroup.getDireccion() + angulo) * bola.getDireccionV());
                    Audio.playSound();
                }
                return true;
            }
        }
        if (bola_X >= 560 && bola_X < 605) {
            if (bola_Y + 20 > CPUGroup.getTranslateY() && bola_Y < CPUGroup.getTranslateY() + 90) {
                if (bola.canIncreaseSpeed()) {
                    bola.cambiarDireccion(1);
                    bola.incrementarVelocidad();
                    bola.setCanIncreaseSpeed(false);
                    double angulo = (bola_Y + 20 - CPUGroup.getTranslateY() - 55) / 1000;
                    bola.setAnguloY(bola_AY + (CPUGroup.getDireccion() + angulo) * bola.getDireccionV());
                    Audio.playSound();
                }
                return true;
            }
        }
        if (bola_Y <= 0) {
            bola.cambiarDireccionV(0);
            return true;
        }
        if (bola_Y >= 460) {
            bola.cambiarDireccionV(1);
            return true;
        }
        if (bola_X > 60 && bola_X < 560) bola.setCanIncreaseSpeed(true);
        return false;
    }

    private void moverBola() {
        double bola_vel = bola.getVelocidad();
        double bola_X = bola.getTranslateX();
        double bola_Y = bola.getTranslateY();
        double bola_AX = bola.getAnguloX();
        double bola_AY = bola.getAnguloY();
        double bola_VD = bola.getDireccionV();


        //Bola salió de pantalla
        if (bola_X < -20) {
            scene.addScoreP2();
            generarBola();
        } else if (bola_X > 640) {
            scene.addScoreP1();
            generarBola();
        } else {
            Timeline tl = new Timeline();
            double mov_X = bola_X + bola_vel / 17 * bola_AX;
            double mov_Y = bola_Y + Math.abs(bola_vel) / 17 * bola_AY * bola_VD;
            KeyValue keyX = new KeyValue(bola.translateXProperty(), mov_X >= 1 || mov_X <= -1 ? mov_X : bola_X + bola_vel / Math.abs(bola_vel));
            KeyValue keyY = new KeyValue(bola.translateYProperty(), mov_Y);
            KeyFrame[] keyFrames = new KeyFrame[10000];
            for (int i = 0; i < 9999; i++) {
                keyFrames[i] = new KeyFrame(Duration.millis((i + 1) * 0.0001), e -> {
                    if (coalisionBola()) {
                        tl.getKeyFrames().removeAll();
                    }
                });
            }
            keyFrames[9999] = new KeyFrame(Duration.millis(1), keyX, keyY);
            tl.getKeyFrames().addAll(keyFrames);
            tl.play();
            tl.setOnFinished(e -> moverBola());
        }
    }

    private void decrementarDificultad() {
        dificultad = dificultad - 1 > 0 ? dificultad - 1 : 1;
    }

    private void incrementarDificultad() {

        dificultad = dificultad + 1 <= 600 ? dificultad + 1 : 600;
    }
}
