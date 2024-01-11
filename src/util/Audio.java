package util;

import javax.sound.sampled.*;

public class Audio {

    private static AudioFormat audioFormat;

    private static DataLine audioLine;
    public static AudioFormat getAudioFormat() {
        if (audioFormat == null) {

            float sampleRate = 8000.0f;
            int sampleSizeInBits = 16;
            boolean signed = true;
            int channels = 2;
            boolean bigEndian = false;
            audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        }

        return audioFormat;
    }

    public static SourceDataLine getSourceDataLine() {
        if (audioLine == null) {

            AudioFormat audioFormat = Audio.getAudioFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            try {
                audioLine = (SourceDataLine) AudioSystem.getLine(info);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }

        return (SourceDataLine) audioLine;
    }
}
