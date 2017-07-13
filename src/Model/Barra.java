package Model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Barra extends Group {
    private double direccion;

    public Barra() {
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
    }

    public void setDireccion(double dir) {
        direccion = dir;
    }

    public double getDireccion() {
        return direccion;
    }
}
