package Modelo;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Audio {
    private static final String Path = new File("audio/ti.mp3").toURI().toString();
    private static final Media media = new Media(Path);
    private static final MediaPlayer player = new MediaPlayer(media);

    public static void playSound() {
        player.stop();
        player.play();
    }

    public static void delayFix() {
        player.setVolume(0);
        player.play();
        player.setOnEndOfMedia(() -> {
            player.setVolume(1);
            player.setOnEndOfMedia(() -> {
            });
        });
    }
}
