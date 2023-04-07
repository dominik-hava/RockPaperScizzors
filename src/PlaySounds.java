import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class PlaySounds {

    public static void playSound(String fileName) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        File fileSound = new File(fileName);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(fileSound);
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
        Thread.sleep(clip.getMicrosecondLength()/1000);
    }
}
