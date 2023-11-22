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
	private ArrayList<String> playedSongs; // zur Speicherung der Filepaths bereits gespielter Songs

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
		this.playedSongs = new ArrayList<>();
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
		System.out.println("\n\n" + manager.getAllSongs());
	}

	void test2() {
		playedSongs.add("Erster Song in Playlist");
		play();
		pause();
	}
	
	/**
	 * Spielt angegeben Song ab
	 * 
	 * @param fileName - Filepath des Songs, der abgespielt werden soll
	 * 
	 * ./02_Drei_Worte.mp3
	 * ./01_Bring_Mich_Nach_Hause.mp3
	 */
	void play(String fileName) {
		if(audioPlayer == null) {
			audioPlayer = minim.loadMP3File(fileName);
			this.actFileName = fileName;
			audioPlayer.play();
			playedSongs.add(actFileName);
			System.out.println("Es wird gespielt: " + this.actFileName);
		}
	}
	
	/**
	 * Wenn die Wiedergabe pausiert ist, wird Wiedergabe fortgesetzt
	 * Wenn kein Song gespielt wurde, wird zufälliger Song abgespielt
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
	 * Ändert die aktuelle Playlist des Players mithilfe des Namens
	 *
	 * @param playlistName - Name der Playlist, die gesetzt werden soll
	 */
	void setPlaylist(String playlistName) {
		int playlistIndex = -1;
		for(int i = 0; i < allPlaylists.size(); i++) {
			if(allPlaylists.get(i).getName().equals(playlistName)) {
				playlistIndex = i;
				break;
			}
		}
		if(playlistIndex != -1) {
			this.actPlaylist = allPlaylists.get(playlistIndex);
		} else {
			System.out.println("Diese Playlist existiert nicht");
		}
		System.out.println("Playlist gesetzt: " + this.actPlaylist.getName());
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
	 * ... Alternativ bei 2x Ausführen (und wenn der Song nicht länger als x sec
	 * 		läuft) wird zum zuletzt gespieltem Song gesprungen
	 */
	void skipBack(){
		int playedSongsSize = playedSongs.size();

		if (playedSongsSize > 1) {
			int lastPlayedIndex = playedSongsSize - 1;
			String lastPlayedSong = playedSongs.get(lastPlayedIndex - 1);
			System.out.println("Springe zurück zu: " + lastPlayedSong);
			play(lastPlayedSong);
		}
		else {
			System.out.println("Vor diesem Song wurden keine Songs abgespielt");
		}
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
