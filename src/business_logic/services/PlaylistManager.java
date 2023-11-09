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

    private ArrayList<Playlist> playlists;

    /*
        alle Playlists sollen am Anfang geladen & erstellt werden
        diese Playlists werden dann in obiger ArrayList gespeichert und können dementsprechend verwaltet werden
        -> zwar bisher nicht erforderlich, aber dennoch sinnvoll

        Besserer Name für getPlaylist wäre loadPlaylist

        Diese Klasse sollte Verzeichnis nach m3u-Dateien durchsuchen und
        für jede die getPlaylist aufrufen

        getPlaylist sollte die geladenen Playlists dann der obigen ArrayList hinzufügen

        MP3Player this.actPlaylist wird dann anders gesetzt
        -> bspw. immer die 1. Playlist aus obiger ArrayList
        -> könnte mit einfachem Getter umgesetzt werden
        -> oder alternativ

            public Playlist getDefaultPlaylist() {
                return playlists.get(0);
            }

     */

    /**
     * hier wird Verzeichnis nach .m3u-Dateien durchsucht
     * für jede .m3u-Datei wird dann getPlaylist() aufgerufen
     */
    private void loadAllPlaylists() {

    }

    /**
     * Liest .m3u-Datei ein und filtert alle .mp3-Dateien mit Dateipfad heraus,
     * erzeugt mit diesen Dateien neue Songs,
     * fügt diese Songs einer Liste hinzu,
     * erstellt neue Playlist mit Namen & Liste der eingelesenen Songs
     *
     * @param filename - .m3u-Datei
     * @return erstellte Playlist
     */
    public Playlist getPlaylist(String filename) {

        ArrayList<Song> songs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            String line;
            String mp3file = ".*\\.mp3"; // Lines, die mit .mp3 enden
            while((line = reader.readLine()) != null) {
                if(line.matches(mp3file)) {
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
}
