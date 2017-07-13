package Modelo;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Bola extends Group {
    private double DireccionV = 1;
    private double Velocidad = 40;
    private double AnguloX = 0.5;
    private double AnguloY = 0.5;
    private boolean canIncreaseSpeed = true;

    public Bola() {
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
}
