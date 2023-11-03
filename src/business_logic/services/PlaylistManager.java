package business_logic.services;

import business_logic.data.Playlist;
import business_logic.data.Song;

import java.io.*;
import java.util.ArrayList;

/**
 * verwaltet Playlisten
 * Funktionen:
 *  - Playlist laden
 *  - ...
 */
public class PlaylistManager {

    /**
     * Liest .m3u-Datei ein und filtert alle -mp3-Dateien mit Dateipfad heraus,
     * erzeugt mit diesen Dateipfaden neue Songs,
     * fügt diese Songs einer ArrayList hinzu,
     * erstellt neue Playlist mit Namen & Liste an Songs
     *
     * @param filename - .m3u-Datei
     * @return erstellte Playlist
     */
    public Playlist getPlaylist(String filename) {

        ArrayList<Song> songs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("./music/default.m3u"))) {

            String line;
            String mp3file = ".*\\.mp3"; // Lines, die mit .mp3 enden
            while((line = reader.readLine()) != null) {
                if(line.matches(mp3file)) {
//                    System.out.println("stimmt überein:");
                    Song song = new Song(line);
                    songs.add(song);
                }
//                System.out.println(line + "\n");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Angegebene Playlist nicht gefunden");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new Playlist("Playlist1", songs);
    }

    Playlist getAllSongs() {
        return null;
    }
}
