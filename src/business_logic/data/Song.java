package business_logic.data;

import com.mpatric.mp3agic.*;
import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Erstellt Song mit Eigenschaften anhand ID3-Tags aus mp3-Datei.
 * Stellt Getter für den Titel und Dateipfad des Songs bereit.
 */
public class Song {

    private StringProperty title = new SimpleStringProperty();
    private StringProperty artist = new SimpleStringProperty();
    private StringProperty filePath = new SimpleStringProperty();
    private IntegerProperty length = new SimpleIntegerProperty();
    private ObjectProperty<Image> albumCover = new SimpleObjectProperty();

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
            this.filePath.set(filepath);
            this.length.set((int) (mp3File.getLengthInSeconds()));

            if (mp3File.hasId3v2Tag()) {
                ID3v2 id3v2tag = mp3File.getId3v2Tag();
                this.title.set(id3v2tag.getTitle());
                this.artist.set(id3v2tag.getArtist());
                this.albumCover.set(readCover(id3v2tag));
            } else if (mp3File.hasId3v1Tag()) {
                ID3v1 id3v1tag = mp3File.getId3v1Tag();
                this.title.set(id3v1tag.getTitle());
                this.artist.set(id3v1tag.getArtist());
            }

            if (this.artist.get() == null || this.artist.get().isEmpty()) {
                this.artist.set("unbekannter Künstler");
            }

        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            /*
             UTE -> unbekannter ID-Tag;
             IDE -> Daten sind beschädigt / verändert
             */
            throw new RuntimeException(e);
        }

    }

    private Image readCover(ID3v2 id3v2) {
        byte[] imageData = id3v2.getAlbumImage();
        if (imageData != null) {
            return new Image(new ByteArrayInputStream(imageData));
        }
        return null;
    }

    public ObjectProperty albumCover() {
        return albumCover;
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getArtist() {
        return artist.get();
    }

    public StringProperty artistProperty() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist.set(artist);
    }

    public String getFilePath() {
        return filePath.get();
    }

    public StringProperty filePathProperty() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath.set(filePath);
    }

    public int getLength() {
        return length.get();
    }

    public IntegerProperty lengthProperty() {
        return length;
    }

    public void setLength(int length) {
        this.length.set(length);
    }
}
