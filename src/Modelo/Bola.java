package Modelo;

import Controlador.Controlador;
import Vista.Vista_juego;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Bola extends Group {
    private final Barra Jugador, CPU;
    private final Controlador controlador;
    private final Vista_juego scene;
    private double DireccionV = 1;
    private double Velocidad = 40;
    private double AnguloX = 0.5;
    private double AnguloY = 0.5;
    private boolean canIncreaseSpeed = true;

    public Bola() {
        Jugador = null;
        CPU = null;
        controlador = null;
        scene = null;
    }

    public Bola(Barra j, Barra c, Controlador con, Vista_juego s) {
        Jugador = j;
        CPU = c;
        controlador = con;
        scene = s;
        Rectangle[] rectangles = new Rectangle[6];
        for (int i = 0; i < 6; i++) {
            rectangles[i] = new Rectangle(20, 20, Color.WHITE);
        }
        rectangles[1].setFill(Color.GRAY);
        rectangles[2].setFill(Color.GRAY);
        rectangles[3].setFill(Color.DARKGRAY);
        rectangles[4].setFill(Color.DARKGRAY);
        rectangles[1].setTranslateX(10);
        rectangles[2].setTranslateX(-10);
        rectangles[3].setTranslateY(10);
        rectangles[4].setTranslateY(-10);
        rectangles[1].setTranslateZ(-10);
        rectangles[2].setTranslateZ(-10);
        rectangles[3].setTranslateZ(-10);
        rectangles[4].setTranslateZ(-10);
        rectangles[5].setTranslateZ(-20);
        rectangles[1].setRotate(90);
        rectangles[2].setRotate(90);
        rectangles[3].setRotate(90);
        rectangles[4].setRotate(90);
        rectangles[1].setRotationAxis(Rotate.Y_AXIS);
        rectangles[2].setRotationAxis(Rotate.Y_AXIS);
        rectangles[3].setRotationAxis(Rotate.X_AXIS);
        rectangles[4].setRotationAxis(Rotate.X_AXIS);
        getChildren().addAll(rectangles);
    }

    public double getVelocidad() {
        return Velocidad;
    }

    public void cambiarDireccion(int direccion) {
        if (direccion == 0) {
            if (Velocidad < 0)
                Velocidad *= -1;

        } else if (direccion == 1) {
            if (Velocidad > 0)
                Velocidad *= -1;
        }

    }

    public void incrementarVelocidad() {
        Velocidad *= 1.1;
    }

    public double getAnguloX() {
        return AnguloX;
    }

    public double getAnguloY() {
        return AnguloY;
    }

    public void setAnguloY(double anguloY) {
        AnguloY = Math.abs(anguloY);
        if (AnguloY >= 0.85) AnguloY = 0.85;
        AnguloX = 1 - AnguloY;
    }

    public void cambiarDireccionV(int dir) {
        switch (dir) {
            case 0:
                if (DireccionV < 0)
                    DireccionV *= -1;
                break;
            case 1:
                if (DireccionV > 0)
                    DireccionV *= -1;
        }

    }

    public double getDireccionV() {
        return DireccionV;
    }

    public boolean canIncreaseSpeed() {
        return canIncreaseSpeed;
    }

    public void setCanIncreaseSpeed(boolean bln) {
        canIncreaseSpeed = bln;
    }

    private boolean coalisionBola() {
        double bola_X = getTranslateX();
        double bola_Y = getTranslateY();
        double bola_AY = getAnguloY();
        if (bola_X > 15 && bola_X <= 60) {
            if (bola_Y + 20 > Jugador.getTranslateY() && bola_Y < Jugador.getTranslateY() + 90) {
                if (canIncreaseSpeed()) {
                    cambiarDireccion(0);
                    incrementarVelocidad();
                    setCanIncreaseSpeed(false);
                    double angulo = ((bola_Y < Jugador.getTranslateY() ? bola_Y + 20 : bola_Y) - (Jugador.getTranslateY() - 45)) / 1000;
                    setAnguloY(bola_AY + (Jugador.getDireccion() + angulo) * getDireccionV());
                    Audio.playSound();
                }
                return true;
            }
        }
        if (bola_X >= 560 && bola_X < 605) {
            if (bola_Y + 20 > CPU.getTranslateY() && bola_Y < CPU.getTranslateY() + 90) {
                if (canIncreaseSpeed()) {
                    cambiarDireccion(1);
                    incrementarVelocidad();
                    setCanIncreaseSpeed(false);
                    double angulo = ((bola_Y < CPU.getTranslateY() ? bola_Y + 20 : bola_Y) - (CPU.getTranslateY() - 45)) / 1000;
                    setAnguloY(bola_AY + (CPU.getDireccion() + angulo) * getDireccionV());
                    Audio.playSound();
                }
                return true;
            }
        }
        if (bola_Y <= 0) {
            cambiarDireccionV(0);
            return true;
        }
        if (bola_Y >= 460) {
            cambiarDireccionV(1);
            return true;
        }
        if (bola_X > 60 && bola_X < 560) setCanIncreaseSpeed(true);
        return false;
    }

    public void moverBola() {
        double bola_vel = getVelocidad();
        double bola_X = getTranslateX();
        double bola_Y = getTranslateY();
        double bola_AX = getAnguloX();
        double bola_AY = getAnguloY();
        double bola_VD = getDireccionV();
        Timeline tl = new Timeline();


        //Bola salió de pantalla
        if (bola_X < -20) {
            scene.addScoreP2();
            controlador.generarBola();
            tl.stop();
        } else if (bola_X > 640) {
            scene.addScoreP1();
            controlador.generarBola();
            tl.stop();
        } else {
            double mov_X = bola_X + bola_vel / 17 * bola_AX;
            double mov_Y = bola_Y + Math.abs(bola_vel) / 17 * bola_AY * bola_VD;
            KeyValue keyX = new KeyValue(translateXProperty(), mov_X >= 1 || mov_X <= -1 ? mov_X : bola_X + bola_vel / Math.abs(bola_vel));
            KeyValue keyY = new KeyValue(translateYProperty(), mov_Y);
            KeyFrame[] keyFrames = new KeyFrame[1000];
            for (int i = 0; i < 999; i++) {
                keyFrames[i] = new KeyFrame(Duration.millis((i + 1) * 0.001), e -> {
                    if (coalisionBola()) {
                        tl.getKeyFrames().removeAll();
                    }
                });
            }
            keyFrames[999] = new KeyFrame(Duration.millis(1), keyX, keyY);
            tl.getKeyFrames().addAll(keyFrames);
            tl.play();
            tl.setOnFinished(e -> moverBola());
        }
    }
}
