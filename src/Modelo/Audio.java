package Modelo;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Audio {
    private static final Media media = new Media("file:///" + System.getProperty("user.dir").replace('\\', '/').replace(" ", "%20") + "/" + "src/audio/ti.mp3");

    public static void playSound() {
        new MediaPlayer(media).play();
    }
}
