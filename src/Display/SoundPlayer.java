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

    SoundPlayer(String filepath) {
        try {
            File file = new File(filepath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    static void move(){
        SoundPlayer moving = new SoundPlayer(filepath_move);
        moving.clip.start();

    }
    static void music(){
        SoundPlayer background_music = new SoundPlayer(filepath_music);
        background_music.loop();
    }
    static void rotate(){
        SoundPlayer rotating = new SoundPlayer(filepath_move); // Rotate ljudet är lite jobbigt att lyssna på
        rotating.clip.start();
    }

    static void clear(){
        SoundPlayer clearing = new SoundPlayer(filepath_clear);
        clearing.clip.start();

    }

    void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }


}
