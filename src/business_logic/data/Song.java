package business_logic.data;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;

/**
 * Erstellt Song anhand ID3-Tags aus mp3-Datei
 */
public class Song {

    private String title;
    private String filePath;
    private int length;

    public Song(String filepath) {
        try {
            Mp3File mp3File = new Mp3File(filepath);

            this.filePath = filepath;
            this.title = mp3File.getFilename();
            this.length = (int) mp3File.getLengthInSeconds();

        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            /*
             UTE -> unbekannter ID-Tag;
             IDE -> Daten sind beschädigt / verändert
             */
            throw new RuntimeException(e);
        }

    }

    public String getTitle() {
        return this.title;
    }
    public String getFilePath() {
        return this.filePath;
    }
}
