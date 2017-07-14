package Modelo;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import static Controlador.Contolador.*;

public class Barra extends Group {
    private double direccion;

    public Barra(boolean Jugador) {
        Rectangle[] rectangles = new Rectangle[4];
        for (int i = 0; i < 4; i++) {
            rectangles[i] = new Rectangle(30, 90, Color.WHITE);
        }
        Rectangle[] cubes = new Rectangle[2];
        for (int i = 0; i < 2; i++) {
            cubes[i] = new Rectangle(30, 30, Color.DARKGRAY);
        }
        getChildren().addAll(rectangles);
        getChildren().addAll(cubes);
        rectangles[1].setFill(Color.GRAY);
        rectangles[2].setFill(Color.GRAY);
        rectangles[1].setTranslateX(15);
        rectangles[2].setTranslateX(-15);
        rectangles[1].setTranslateZ(-15);
        rectangles[2].setTranslateZ(-15);
        rectangles[3].setTranslateZ(-30);
        rectangles[1].setRotationAxis(Rotate.Y_AXIS);
        rectangles[2].setRotationAxis(Rotate.Y_AXIS);
        rectangles[1].setRotate(90);
        rectangles[2].setRotate(90);
        cubes[0].setTranslateZ(-15);
        cubes[0].setTranslateY(-15);
        cubes[1].setTranslateZ(-15);
        cubes[1].setTranslateY(75);
        cubes[0].setRotationAxis(Rotate.X_AXIS);
        cubes[1].setRotationAxis(Rotate.X_AXIS);
        cubes[0].setRotate(90);
        cubes[1].setRotate(90);

        if (Jugador) {
            movimientoP1();
        } else movilidadCPU();
    }

    public double getDireccion() {
        return direccion;
    }

    public void setDireccion(double dir) {
        direccion = dir;
    }

    private void movilidadCPU() {
        Task<Void> CPU = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                double bola_Y = getBola_Y();
                double movimiento = 0;
                if (bola_Y > getTranslateY() + 60)
                    if (getTranslateY() < 480 - 90) {
                        movimiento = 8;
                        setDireccion(0.2);
                    } else setDireccion(0);
                else if (bola_Y < getTranslateY() + 50)
                    if (getTranslateY() > 0) {
                        movimiento = -7;
                        setDireccion(-0.2);
                    } else setDireccion(0);

                Timeline tl = new Timeline(
                        new KeyFrame(Duration.millis((600 / getDificultad()) >= 1 ? 600 / getDificultad() : 1),
                                new KeyValue(translateYProperty(), getTranslateY() + movimiento))
                );
                tl.setOnFinished(e -> {
                    try {
                        call();
                    } catch (Exception ignored) {
                    }
                });
                tl.play();
                return null;
            }
        };
        Thread CPU1 = new Thread(CPU);
        CPU1.start();
    }

    private void movimientoP1() {
        Task<Void> P1 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    double movimiento = getDireccion() * 5 * getVelocidad();
                    Timeline tl = new Timeline();
                    KeyFrame[] keyFrames = new KeyFrame[50];
                    for (int i = 0; i < 49; i++) {
                        keyFrames[i] = new KeyFrame(Duration.millis(i + 1), e -> {
                            if ((getTranslateY() <= 0 && getDireccion() <= 0)
                                    ||
                                    (getTranslateY() >= 480 - 90 && getDireccion() >= 0)) {
                                tl.stop();
                                try {
                                    call();
                                } catch (Exception ignored) {
                                }
                            }
                        });
                    }
                    keyFrames[49] = new KeyFrame(Duration.millis(50), new KeyValue(translateYProperty(), getTranslateY() + movimiento));

                    tl.getKeyFrames().addAll(keyFrames);
                    tl.setOnFinished(e -> {
                        try {
                            call();
                        } catch (Exception ignored) {
                        }
                    });
                    tl.play();
                } catch (Exception ignored) {
                }
                return null;
            }
        };
        Thread p1 = new Thread(P1);
        p1.start();
    }
}
