package business_logic.services;

import business_logic.data.Song;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Verwaltet alle Properties des MP3Players
 */
public class PlayerState {

    private MP3Player player;
    private ObjectProperty<Song> currentSong = new SimpleObjectProperty<>();
    private IntegerProperty currentTime = new SimpleIntegerProperty();

    public PlayerState(MP3Player player) {
        this.player = player;
    }

}
