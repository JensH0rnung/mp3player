package business_logic.services;

import business_logic.data.Playlist;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

import java.util.ArrayList;

/**
 Funktionen des Players:
 - play [filename] - verwendet SimpleAudioPlayer zum Abspielen von mp3-Dateien
 - pause - pausiert aktuelle Wiedergabe
 - volume [0-1] - ändert die WiedergabeLautstärke
 - ... - spielt eine Playlist ab
 */
public class MP3Player {

	// Test
	public static final String song = "./music/Empire_State_Of_Mind.mp3";
	
	private SimpleMinim minim;
	private SimpleAudioPlayer audioPlayer;
	private PlaylistManager manager;
	private ArrayList<Playlist> allPlaylists;
	private Playlist actPlaylist;
	private String actFileName;
	private boolean shuffle;
	private boolean repeat;
	private ArrayList<Integer> playedSongs; // zur Speicherung der Indizes bereits gespielter Songs

	// Getter für Shuffle & Repeat
	public boolean isOnShuffle() {
		return this.shuffle;
	}

	public boolean isOnRepeat() {
		return this.repeat;
	}

	MP3Player(){
		this.minim = new SimpleMinim(true);
		this.manager = new PlaylistManager();
		this.allPlaylists = manager.loadAllPlaylists();	// lädt alle Playlists aus Verzeichnis
		this.actPlaylist = allPlaylists.get(0);
		// Standardwerte
		this.shuffle = false;
		this.repeat = false;
	}

	void test() {
		System.out.println("Alle Playlists:");
		for(Playlist list: allPlaylists) {
			System.out.println(list.getName());
		}
		System.out.println("\nAktuelle Playlist: " + this.actPlaylist.getName());
	}

	void test2() {
		play();
		pause();
	}
	
	/**
	 * Spielt angegeben Song ab
	 * 
	 * @param fileName - Name des Songs, der gespielt werden soll
	 * 
	 * ./02_Drei_Worte.mp3
	 * ./01_Bring_Mich_Nach_Hause.mp3
	 */
	void play(String fileName) {
		if(audioPlayer == null) {
			audioPlayer = minim.loadMP3File(fileName);
			this.actFileName = fileName;
			play();
		}
		// den anderen Thread stoppen und neuen mit gegebenem Song starten
//		else {
//			System.out.println("Es wird bereits in Song gespielt");
//		}
	}
	
	/**
	 * Wenn ein Song pausiert wurde, wird Wiedergabe fortgesetzt
	 * Wenn kein Song ausgewählt war, wird zufälliger Song abgespielt
	 */
	void play() {
		// Wiedergabe fortsetzen
		try {
			audioPlayer.play();
		// zufälligen Song spielen
		} catch (NullPointerException e) {
			String randomSong;
			int randomSongIndex, countAllSongs;

			countAllSongs = manager.getAllSongs().size();
			randomSongIndex = (int) (Math.random() * countAllSongs);

			randomSong = manager.getAllSongs().get(randomSongIndex).getFilePath();
			play(randomSong);
			System.out.println("Zufällige Wiedergabe");
		}
	}

	/**
	 * Pausiert die Wiedergabe
	 */
	void pause() {
		audioPlayer.pause();
	}

	/**
	 * Setzt die Lautstärke auf den angegebenen Wert
	 * @param volume - Lautstärke 0 = muted 1 = volle Lautstärke
	 */
	void volume(float volume) {
		// Wert in Dezibel umrechnen, da setGain mit Decibel arbeitet
		float decibel = (float) (20* Math.log10(volume));
		audioPlayer.setGain(decibel);
	}

	/**
	 * denke mal es sollen beim Laden alle Playlisten geladen & initialisiert werden
	 * dann können diese über den Namen gesetzt werden
	 */
	void setActPlaylist(Playlist actPlaylist) {
		/*
		 wie setzt man hier ne Playlist, die zuvor erstellt wurde?
		 */
	}

	/**
	 * Berechnet nächsten Song, abhöngig vom Shuffle-Modus
	 *
	 * 1. Shuffle on -> nächster Song = zufälliger Index, aber nicht der gleiche wie aktueller
	 * 2. Shuffle off -> nächster Song = Index des aktuellen Songs + 1
	 */
	void skip(){
		// Index von aktuellem Song suchen
		int actSongIndex = 0;
		for (int i = 0; i < actPlaylist.getSongs().size(); i++) {
			if (actPlaylist.getSongs().get(i).toString().equals(this.actFileName)) {
				actSongIndex = i;
				break;
			}
		}

		// ans Ende vom aktuellen Song springen
		int songLength = audioPlayer.length();
		audioPlayer.skip(songLength);

		String nextSong;
		int nextSongIndex;
		int playlistLength = actPlaylist.getSongs().size();

		// nächsten Song der Playlist oder zufälligen Song spielen
		if(this.shuffle) {
			do {
				nextSongIndex = (int) (Math.random() * playlistLength);
			} while(nextSongIndex == actSongIndex);
        } else {
			nextSongIndex = actSongIndex + 1;
        }
		// Filepath des nächsten Songs suchen & play-Methode übergeben
		nextSong = actPlaylist.getSongs().get(nextSongIndex).getFilePath();
		System.out.println("Playing next:\t" + nextSong);
		play(nextSong);
	}

	/**
	 * Springt zum zuletzt abgespielten Song zurück
	 * ... Alternativ wird zum Songanfang gesprungen (wenn Song länger als x sec läuft)
	 * ... bei 2x Ausführen (und wenn der Song nicht länger als x sec
	 * 		läuft) wird zum zuletzt gespieltem Song gesprungen
	 */
	void skipBack(){
		// hier könnte man eine Liste verwenden, der jedes Mal der Index des Songs hinzugefügt wird, wenn er gespielt wird
		// bei skipback wird diese Liste dann von hinten nach vorne durchgelaufen und die jeweiligen Songs gespielt
	}

	/**
	 * Aktiviert den Shuffle-Modus des Players
	 * -> der nächste Song wird zufällig ausgewählt
	 *
	 * @param on - shuffle aktiviert / deaktiviert
	 */
	void shuffle(boolean on) {
        this.shuffle = on;
	}

	/**
	 * Aktiviert den Repeat-Modus des Players
	 * -> der gleiche Song wird in Dauerschleife abgespielt
	 *
	 * @param on - repeat aktiviert / deaktiviert
	 */
	void repeat(boolean on) {
		this.repeat = on;
		// Loop starten
		if(this.repeat && !audioPlayer.isLooping()) {
			audioPlayer.loop();
		// Loop beenden
		} else if(!this.repeat && audioPlayer.isLooping()) {
			//Loop deaktivieren - Manual -> play()
			audioPlayer.play();
		}
	}
}
