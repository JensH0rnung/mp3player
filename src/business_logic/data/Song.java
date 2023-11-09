package business_logic.data;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;

public class Song {

    /**
     * Informationen, die aus ID3-Tags der mp3-Datei gelesen werden
     */
    private String title;
    private int length;

    public Song(String filepath) {
        try {
            Mp3File mp3File = new Mp3File(filepath);

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

    String getTitle() {
        return this.title;
    }
}
