package Modelo;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Audio {
    private static final MediaPlayer player = new MediaPlayer(new Media(new File("audio/ti.mp3").toURI().toString()));

    static void playSound() {
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
