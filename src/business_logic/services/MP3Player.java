package business_logic.services;

import business_logic.data.Playlist;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

import java.util.ArrayList;

/**
 * Funktionen des Players:
 * 	- play [filename] - verwendet SimpleAudioPlayer zum Abspielen von mp3-Dateien
 * 	- play - spielt einen zufällig gewählten Song aus allen verfügbaren Songs
 * 	- pause - pausiert die Wiedergabe
 * 	- volume [0-1] - ändert die Wiedergabe-Lautstärke (0 -> leise; 1 -> volle Lautstärke)
 * 	- setPlaylist [playlistName] - setzt die zu spielende Playlist
 * 	- skip - springt zum nächsten Song (berücksichtigt Shuffle)
 * 	- skipback - springt zum zuletzt gespielten Song
 * 	- shuffle [on | off] - beeinflusst die Wahl des nächsten Songs (Reihenfolge der Playlist / zufällige Reihenfolge)
 *  - repeat [on | off] - spielt, bei Aktivierung, den aktuellen Song in Dauerschleife
 */
public class MP3Player {
	
	private SimpleMinim minim;
	private SimpleAudioPlayer audioPlayer;
	private PlaylistManager manager;
	private ArrayList<Playlist> allPlaylists;
	private Playlist actPlaylist;
	private String actFileName;
	private boolean shuffle;
	private boolean repeat;
	private ArrayList<String> playedSongs; // zur Speicherung der Filepaths bereits gespielter Songs

	/**
	 * Getter, ob der Player gerade etwas abspielt
	 * @return - true | false
	 */
	public boolean isPlaying() {
		return audioPlayer != null && audioPlayer.isPlaying();
	}

	/**
	 * Getter für ShuffleModus
	 * @return - shuffle on | off
	 */
	public boolean isOnShuffle() {
		return this.shuffle;
	}

	/**
	 * Getter für RepeatModus
	 * @return - repeat on | off
	 */
	public boolean isOnRepeat() {
		return this.repeat;
	}

	/**
	 * Constructor
	 */
	public MP3Player(){
		this.minim = new SimpleMinim(true);
		this.manager = new PlaylistManager();
		this.allPlaylists = manager.loadAllPlaylists();	// lädt alle Playlists aus Verzeichnis
		this.actPlaylist = allPlaylists.get(0);
		this.playedSongs = new ArrayList<>();
		// Standardwerte
		this.shuffle = false;
		this.repeat = false;
	}

//	void test() {
//		System.out.println("--- All Playlists in Library ---");
//		for(Playlist list: allPlaylists) {
//			System.out.println(list.getName());
//		}
//		System.out.println("\nActPlaylist -> " + this.actPlaylist.getName());
//		System.out.println("\n--- All Songs in Library ---");
//		for(Song song: manager.getAllSongs()) {
//			System.out.println(song.getTitle());
//		}
//	}
//
//	void test2() {
//		playedSongs.add("Erster Song in Playlist");
//		play();
//		pause();
//	}
	
	/**
	 * Spielt angegeben Song ab
	 * 
	 * @param fileName - Filepath des Songs, der abgespielt werden soll
	 * 
	 * ./02_Drei_Worte.mp3
	 * ./01_Bring_Mich_Nach_Hause.mp3
	 */
	public void play(String fileName) {
		if(audioPlayer == null) {
			audioPlayer = minim.loadMP3File(fileName);
			this.actFileName = fileName;
			audioPlayer.play();
			playedSongs.add(actFileName);
		}
	}
	
	/**
	 * Wenn die Wiedergabe pausiert ist, wird Wiedergabe fortgesetzt
	 * Wenn kein Song gespielt wurde, wird zufälliger Song abgespielt
	 */
	public void play() {
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
	public void pause() {
		audioPlayer.pause();
	}

	/**
	 * Setzt die Lautstärke auf den angegebenen Wert
	 * @param volume - Lautstärke 0 = muted 1 = volle Lautstärke
	 */
	public void volume(float volume) {
		// Wert in Dezibel umrechnen, da setGain mit Decibel arbeitet
		float decibel = (float) (20* Math.log10(volume));
		audioPlayer.setGain(decibel);
	}

	/**
	 * Ändert die aktuelle Playlist des Players mithilfe des Namens
	 *
	 * @param playlistName - Name der Playlist, die gesetzt werden soll
	 */
	public void setPlaylist(String playlistName) {
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
	public void skip(){
		System.out.println("Button pressed");
		if(audioPlayer != null) {
			System.out.println("akt Wiedergabe");
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

			String nextSongPath, nextSongName;
			int nextSongIndex;
			int playlistLength = actPlaylist.getSongs().size();

			// nächsten Song der Playlist oder zufälligen Song spielen
			if (this.shuffle) {
				do {
					nextSongIndex = (int) (Math.random() * playlistLength);
				} while (nextSongIndex == actSongIndex);
			} else {
				nextSongIndex = actSongIndex + 1;
			}
			// Filepath des nächsten Songs suchen & play-Methode übergeben
			nextSongPath = actPlaylist.getSongs().get(nextSongIndex).getFilePath();
			nextSongName = actPlaylist.getSongs().get(nextSongIndex).getTitle();
			System.out.println("Playing next:\t" + nextSongName);
			play(nextSongPath);
		}
	}

	/**
	 * Springt zum zuletzt abgespielten Song zurück
	 * ... Alternativ wird zum Songanfang gesprungen (wenn Song länger als x sec läuft)
	 * ... Alternativ bei 2x Ausführen (und wenn der Song nicht länger als x sec
	 * 		läuft) wird zum zuletzt gespieltem Song gesprungen
	 */
	public void skipBack(){
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

//	/**
//	 * Aktiviert den Shuffle-Modus des Players
//	 * -> der nächste Song wird zufällig ausgewählt
//	 *
//	 * @param on - shuffle aktiviert / deaktiviert
//	 */
//	public void oldShuffle(boolean on) {
//        this.shuffle = on;
//	}

	/**
	 * Aktiviert / deaktiviert den Shuffle-Modus des Players
	 */
	public void shuffle() {
        this.shuffle = !this.shuffle;
		System.out.println("Shuffle - " + this.shuffle);
	}

//	/**
//	 * Aktiviert den Repeat-Modus des Players
//	 * -> der gleiche Song wird in Dauerschleife abgespielt
//	 *
//	 * @param on - repeat aktiviert / deaktiviert
//	 */
//	public void oldRepeat(boolean on) {
//		this.repeat = on;
//		// Loop starten
//		if(this.repeat && !audioPlayer.isLooping()) {
//			audioPlayer.loop();
//		// Loop beenden
//		} else if(!this.repeat && audioPlayer.isLooping()) {
//			//Loop deaktivieren - Manual -> play()
//			audioPlayer.play();
//		}
//	}

	/**
	 * Aktiviert / deaktiviert den Repeat-Modus des Players
	 */
	public void repeat() {
		this.repeat = !this.repeat;
		System.out.println("Repeat - " + this.repeat);
	}
}
