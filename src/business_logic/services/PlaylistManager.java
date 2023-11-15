package business_logic.services;

import business_logic.data.Playlist;
import business_logic.data.Song;

import java.io.*;
import java.util.ArrayList;

/**
 * Verwaltet Playlisten
 * Funktionen:
 *  - Playlist laden
 *  - ...
 */
public class PlaylistManager {

    private ArrayList<Playlist> allPlaylists = new ArrayList<>();
    private static final String musicDir = "./music";
    private static final String m3uFile = ".*\\.m3u";
    private static final String mp3File = ".*\\.mp3";
    private int playlistCount; // Anzahl Playlists im music-Ordner

    /**
     * Hier wird Verzeichnis nach .m3u-Dateien durchsucht (mithilfe der File-Klasse),
     * für jede .m3u-Datei wird createPlaylist() aufgerufen
     * -> alle Playlists im music-Ordner werden erstellt
     *
     * @return - alle Playlists aus dem music-Ordner
     */
    public ArrayList<Playlist> loadAllPlaylists() {

        File directory = new File(musicDir); // File-Objekt anhand Dateipfad erstellen

        // wenn dir existiert & vom Typ dir ist
        if(directory.isDirectory() && directory.exists()) {
            File[] files = directory.listFiles();

            // wenn dir nicht leer ist, suche .m3u-Dateien
            if(files != null) {
                for(File file: files) {
                    if (file.getName().matches(m3uFile)) {
                        allPlaylists.add(createPlaylist(file));
                    }
                }
            } else {
                System.out.println("Verzeichnis enthält keine Dateien");
            }
        } else {
            System.out.println("Verzeichnis existiert nicht oder ist kein Verzeichnis");
        }
        return this.allPlaylists;
    }

    /**
     * Erstellt Playlist aus .m3u-File:
     *  - .m3u-File wird nach Dateipfaden mit der Endung .m3p durchsucht
     *  - mit diesen Dateipfaden werden Songs erstellt und der Songliste hinzugefügt
     *  - Namen der Playlists werden momentan durchnummiert
     *  - Playlist wird mit Namen und Songliste erstellt
     *
     * @param m3uFile - m3u-Datei aus dem music-Ordner
     */
    private Playlist createPlaylist(File m3uFile) {
        playlistCount++;
        String playlistName = "Playlist" + playlistCount;
        ArrayList<Song> songs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(m3uFile))) {

            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.matches(mp3File)) {
                    Song song = new Song(line);
                    songs.add(song);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Datei wurde nicht gefunden");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Playlist(playlistName, songs);
    }
}
