package interfaz.sounds;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class SoundManager {

    private static MediaPlayer backgroundPlayer;

    // 🎵 Música de fondo
    public static void playBackground(String rutaResource) {
        try {
            if (backgroundPlayer != null) {
                backgroundPlayer.stop();
            }

            URL resource = SoundManager.class.getResource(rutaResource);
            if (resource == null) {
                throw new RuntimeException("No se encontró el archivo: " + rutaResource);
            }

            Media media = new Media(resource.toExternalForm());
            backgroundPlayer = new MediaPlayer(media);
            backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundPlayer.setVolume(0.8);
            backgroundPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔊 Efectos de sonido
    public static void playEffect(String rutaResource) {
        try {
            URL resource = SoundManager.class.getResource(rutaResource);
            if (resource == null) {
                throw new RuntimeException("No se encontró el archivo: " + rutaResource);
            }

            Media media = new Media(resource.toExternalForm());
            MediaPlayer player = new MediaPlayer(media);
            player.setVolume(0.8);
            player.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopBackground() {
        if (backgroundPlayer != null) {
            backgroundPlayer.stop();
        }
    }
}
