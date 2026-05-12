import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Hilo extends Thread {

    private Clip clip;

    @Override
    public void run() {

        try {

            URL ruta = getClass().getResource(
                    "/Musica/musicaFondo.wav");

            AudioInputStream audio =
                    AudioSystem.getAudioInputStream(ruta);

            clip = AudioSystem.getClip();

            clip.open(audio);

            // repetir infinitamente
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            clip.start();

        } catch (
                UnsupportedAudioFileException
                | IOException
                | LineUnavailableException e
        ) {

            System.out.println(
                    "Error al reproducir música"
            );
        }
    }

    public void detenerMusica() {

        if (clip != null) {

            clip.stop();
            clip.close();
        }
    }
}