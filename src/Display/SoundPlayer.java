package Display;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {

    private Clip clip;
    static final String filepath_music = "src/wav/musicfile.wav";
    static final String filepath_clear = "src/wav/clear.wav";
    static final String filepath_move = "src/wav/move.wav";
    static final String filepath_rotate = "src/wav/rotate.wav";

    public SoundPlayer(String filepath) {
        try {
            File file = new File(filepath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    public static void move(){
        SoundPlayer moving = new SoundPlayer(filepath_move);
        moving.clip.start();

    }
    public static void music(){
        SoundPlayer background_music = new SoundPlayer(filepath_music);
        background_music.loop();
    }
    public static void rotate(){
        SoundPlayer rotating = new SoundPlayer(filepath_rotate);
        rotating.clip.start();
    }

    public static void clear(){
        SoundPlayer clearing = new SoundPlayer(filepath_clear);
        clearing.clip.start();

    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }


}
