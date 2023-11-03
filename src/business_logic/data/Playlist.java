package business_logic.data;

import java.util.ArrayList;

/**
 * Playlist verwaltet mehrere Songs
 * Songs der Playlist sind in default.m3u gespeichert
 */
public class Playlist {

    private String name;
    private ArrayList<Song> songs;

    public Playlist(String name, ArrayList<Song> songs) {
        this.name = name;
        this.songs = songs;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }
}
