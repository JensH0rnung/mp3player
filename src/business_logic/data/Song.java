package business_logic.data;

import com.mpatric.mp3agic.*;

import java.io.IOException;

/**
 * Erstellt Song mit Eigenschaften anhand ID3-Tags aus mp3-Datei.
 * Stellt Getter für den Titel und Dateipfad des Songs bereit.
 */
public class Song {

    private String title;
    private String filePath;
    private int length;

    /**
     * Erstellt Songs anhand eines Dateipfades und setzt dessen Eigenschaften:
     *  1. filePath - wird benötigt um einen Song abzuspielen
     *  2. length - die Länge des Songs in Sekunden
     *  3. title - der Titel des Songs, der im Player angezeigt werden soll
     *     -> Überprüfung, ob ID3v2-Tag oder ID3v1-Tag vorhanden ist
     *
     * @param filepath - Dateipfad des Songs aus .m3u-Datei
     */
    public Song(String filepath) {
        try {
            Mp3File mp3File = new Mp3File(filepath);
            this.filePath = filepath;
            this.length = (int) mp3File.getLengthInSeconds();

            if (mp3File.hasId3v2Tag()) {
                ID3v2 id3v2tag = mp3File.getId3v2Tag();
                this.title = id3v2tag.getTitle();
            } else if (mp3File.hasId3v1Tag()) {
                ID3v1 id3v1tag = mp3File.getId3v1Tag();
                this.title = id3v1tag.getTitle();
            }

        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            /*
             UTE -> unbekannter ID-Tag;
             IDE -> Daten sind beschädigt / verändert
             */
            throw new RuntimeException(e);
        }

    }

    /**
     * Getter für Songtitel
     * @return - Titel des Songs
     */
    public String getTitle() {
        return this.title;
    }
    /**
     * Getter für Dateipfad des Songs
     * @return - Dateipfad des Songs
     */
    public String getFilePath() {
        return this.filePath;
    }
}
