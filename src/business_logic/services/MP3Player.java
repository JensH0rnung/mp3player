package business_logic.services;

import business_logic.data.Playlist;
import business_logic.data.Song;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.*;

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
	private boolean countTimeCalled;
	private ArrayList<String> playedSongs; // zur Speicherung der Filepaths bereits gespielter Songs
	/**
	 * bei jedem Skip +1
	 * bei jedem BackSkip -1
	 * -> aktuelle Position in playedSongs
	 */
	private int actPositionInPlayedSongs;

	private Thread playThread;
	private Thread countTimeThread;

	private ObjectProperty<Song> currentSong = new SimpleObjectProperty<>();
	private IntegerProperty currentTime = new SimpleIntegerProperty();
	private FloatProperty currentVolume = new SimpleFloatProperty(0.5F);  // Standardlautstärke

	public float getCurrentVolume() {
		return currentVolume.get();
	}

	public FloatProperty currentVolumeProperty() {
		return currentVolume;
	}

	private StringProperty shuffleStyle = new SimpleStringProperty();
	private StringProperty repeatStyle = new SimpleStringProperty();
	private StringProperty muteStyle = new SimpleStringProperty();
	private StringProperty playButtonText = new SimpleStringProperty("Play");

	private BooleanProperty shuffleState = new SimpleBooleanProperty();
	private BooleanProperty repeatState = new SimpleBooleanProperty();
	private BooleanProperty muteState = new SimpleBooleanProperty();

	/**
	 * Constructor
	 */
	public MP3Player(){
		this.minim = new SimpleMinim();
//		this.minim = new SimpleMinim(true);   // temporäres Threading
		this.manager = new PlaylistManager();
		this.allPlaylists = manager.loadAllPlaylists();	// lädt alle Playlists aus Verzeichnis
		this.actPlaylist = allPlaylists.get(0);  // hier 1 für 30s TestSong
		this.playedSongs = new ArrayList<>();
		// Standardwerte
		this.countTimeCalled = false;
		this.actPositionInPlayedSongs = 0;  // Position in playedSongs
	}

	/**
	 * Setzt angegeben Song & startet die Wiedergabe
	 *
	 * @param songFilePath - Filepath des Songs, der abgespielt werden soll
	 *
	 */
	public void play(String songFilePath) {

		minim.stop();

		audioPlayer = minim.loadMP3File(songFilePath);
		this.actFileName = songFilePath;
		playedSongs.add(actFileName);

		// setzt bei jeder neuen Wiedergabe die vorherige Lautstärke, bzw. muted diese
		setVolume(currentVolume.get());
		if (isMuted()) {
			audioPlayer.mute();
		}

		updateCurrentSong(actFileName);
		resetCurrentPlayTime();

		play();
	}

	/**
	 * Wenn die Wiedergabe pausiert ist, wird Wiedergabe fortgesetzt
	 * Wenn kein Song gespielt wurde, wird abhängig vom Shuffle-Modus,
	 * ein zufälliger Song oder der 1. Song der Playlist gestartet
	 */
	public void play() {
		playThread = new Thread(() -> {
			// wenn bereits eine Wiedergabe gestartet wurde
			if(audioPlayer != null) {

				// Test für Autoskip
//				System.out.println("Test für Autoskip");
//				audioPlayer.skip(audioPlayer.length() - 10000);
//				currentTimeProperty().set((audioPlayer.length() - 10000) / 1000);

				audioPlayer.play();
			}
			else {
				if(isOnShuffle()) {
					// zufälligen Song spielen (unabghängig von Playlist)
					String randomSongPath = getRandomSongPath();
					currentSong.set(new Song(randomSongPath));

					play(randomSongPath);
				} else {
					// Playlist starten
					String firstSongInPlaylist = actPlaylist.getSongs().get(0).getFilePath();
					currentSong.set(new Song(firstSongInPlaylist));

					play(firstSongInPlaylist);
				}
			}

			/*
			Autoskip ggf. anpassen
				ca. 2s vor Songende wird geskipped
				(mit kleinerem Wert geht es nicht)
			 */
			if(audioPlayer.position() >= audioPlayer.length() - 2000) {
				skip();
			}
		});
		playThread.setDaemon(true);
		playThread.start();
	}

	/**
	 * Zählt die Dauer der aktuellen Wiedergabe
	 * etwas ungünstig mit dem Sleep, aber andernfalls kann NullPointerException geworfen werden
	 */
	public void countTime() {
		// für einmaligen Aufruf aus Liste
		countTimeCalled = true;

		countTimeThread = new Thread(() -> {
			// Umgehung, der NullPointerException bei Erst-Wiedergabe aus Liste
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			// zählt pro Sekunde um 1 hoch
			while (audioPlayer.isPlaying() && currentTime.get() <= currentSong.get().getLength()) {
					currentTime.set(currentTime.get() + 1);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}

		});
		countTimeThread.setDaemon(true);
		countTimeThread.start();
	}

	/**
	 * Pausiert die Wiedergabe
	 */
	public void pause() {
		if(audioPlayer.isPlaying()) {
			audioPlayer.pause();
		}
	}

	/**
	 * Setzt die Lautstärke auf den angegebenen Wert
	 * @param volume - Lautstärke 0 = muted 1 = volle Lautstärke
	 */
	public void setVolume(float volume) {
		if(audioPlayer != null) {
			// Wert in Dezibel umrechnen, da setGain mit Decibel arbeitet
			float decibel = (float) (20 * Math.log10(volume));
			audioPlayer.setGain(decibel);
			currentVolume.set(volume);
		}
	}

	/**
	 * Schaltet die Wiedergabe auf Stumm - und wieder auf laut
	 */
	public void mute() {
		if(audioPlayer != null) {
			if (isMuted()) {
				muteStateProperty().set(false);
				audioPlayer.unmute();
			} else {
				muteStateProperty().set(true);
				audioPlayer.mute();
			}
		}
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
	public void skip() {
		if(audioPlayer != null) {
			// Index von aktuellem Song suchen
			int actSongIndex = getActSongIndex(actFileName);
			// bisher nicht relevante Info
			if(actSongIndex == -1) {
				System.out.println("Song ist nicht in aktueller Playlist");
			}

			String nextSongPath;
			int nextSongIndex = -1;
			int playlistLength = actPlaylist.getSongs().size();

			if(repeatStateProperty().get()) {
				audioPlayer.rewind();
			} else {
				// nächsten oder zufälligen Song der Playlist spielen
				if (shuffleStateProperty().get()) {
					do {
						nextSongIndex = (int) (Math.random() * playlistLength);
					} while (nextSongIndex == actSongIndex);
				} else {
					if (actSongIndex + 1 < actPlaylist.getSongs().size()) {
						nextSongIndex = actSongIndex + 1;
					}
				}

			}
			// Filepath des nächsten Songs suchen & play-Methode übergeben
			try {
				nextSongPath = actPlaylist.getSongs().get(nextSongIndex).getFilePath();
				updateCurrentSong(nextSongPath);
				play(nextSongPath);
				actPositionInPlayedSongs++;
			} catch(IndexOutOfBoundsException e) {
				System.out.println("Rewind");
			}
			resetCurrentPlayTime();
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

        if (audioPlayer != null && actFileName.equals(playedSongs.get(0))){
            // Song neu starten
			System.out.println("Vor diesem Song wurden keine Songs abgespielt");
			audioPlayer.rewind();
        }
        if (playedSongsSize > 1) {

            if (actPositionInPlayedSongs > 0) {
				actPositionInPlayedSongs--;
			}
			String lastPlayedSong = playedSongs.get(actPositionInPlayedSongs);

			updateCurrentSong(lastPlayedSong);
			play(lastPlayedSong);
		}
        resetCurrentPlayTime();
	}

	/**
	 * Aktiviert / deaktiviert den Shuffle-Modus des Players
	 */
	public void shuffle() {
		if(isOnShuffle()) {
			shuffleStateProperty().set(false);
		} else {
			shuffleStateProperty().set(true);
		}
	}

	/**
	 * Aktiviert / deaktiviert den Repeat-Modus des Players
	 */
	public void repeat() {
		if(isOnRepeat()) {
			repeatStateProperty().set(false);
		} else {
			repeatStateProperty().set(true);
		}
	}

	/**
	 * Setzt die aktuelle Wiedergabezeit zurück
	 * Wird nach jedem Skip oder Skipback aufgerufen
	 */
	private void resetCurrentPlayTime() {
		currentTimeProperty().set(0);
	}

	/**
	 * Gibt den Index des aktuellen Songs zurück,
	 * wenn sich dieser Song in der aktuellen Playlist befindet
	 * @return - Index des aktuellen Songs
	 */
	private int getActSongIndex(String actFileName) {
		for (int i = 0; i < actPlaylist.getSongs().size(); i++) {
			if (actPlaylist.getSongs().get(i).getFilePath().equals(actFileName)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Aktualisiert die currentSongProperty,
	 * damit die Infos korrekt in der GUI angezeigt werden
	 *
	 * @param filePath - FilePath des Songs, dessen Infos angezeigt werden sollen
	 */
	public void updateCurrentSong(String filePath) {
			Song newSong = new Song(filePath);
			currentSong.set(newSong);
	}

	/**
	 * Damit bei Songauswahl aus Liste auch aktualisiert wird
	 */
	public void incActPositionInPlayedSongs() {
		// Bedingung, damit Index erst bei 2. Auswahl erhöht wird
		if(!playedSongs.isEmpty()) {
			actPositionInPlayedSongs++;
		}
	}

	/**
	 * Wählt zufälligen Song aus allen Songs aus
	 * @return - Dateipfad des zufälligen Songs
	 */
	private String getRandomSongPath() {
		String randomSongPath;
		int randomSongIndex, countAllSongs;
		countAllSongs = manager.getAllSongs().size();

		randomSongIndex = (int) (Math.random() * countAllSongs);

		randomSongPath = manager.getAllSongs().get(randomSongIndex).getFilePath();

		return randomSongPath;
	}

	/**
	 * Getter für die aktuelle Playlist
	 * @return - aktuelles Playlist-Objekt
	 */
	public Playlist getActPlaylist() {
		return actPlaylist;
	}

	/**
	 * Getter, ob der Player gerade etwas abspielt
	 * @return - true | false
	 */
	public boolean isPlaying() {
		return audioPlayer != null && audioPlayer.isPlaying();
	}

	/**
	 * Getter für aktuelles Song-Objekt
	 * @return - Song als Property-Objekt
	 */
	public ObjectProperty<Song> currentSongProperty() {
		return currentSong;
	}

	/**
	 * Getter für die aktuelle Wiedergabezeit
	 * @return - Zeit in s
	 */
	public IntegerProperty currentTimeProperty() {
		return currentTime;
	}

	public boolean isOnShuffle() {
		return shuffleState.get();
	}

	public BooleanProperty shuffleStateProperty() {
		return shuffleState;
	}

	public boolean isOnRepeat() {
		return repeatState.get();
	}

	public BooleanProperty repeatStateProperty() {
		return repeatState;
	}

	public boolean isMuted() {
		return muteState.get();
	}

	public BooleanProperty muteStateProperty() {
		return muteState;
	}

	public String getShuffleStyle() {
		return shuffleStyle.get();
	}

	public StringProperty shuffleStyleProperty() {
		return shuffleStyle;
	}

	public void setShuffleStyle(String shuffleStyle) {
		this.shuffleStyle.set(shuffleStyle);
	}

	public String getRepeatStyle() {
		return repeatStyle.get();
	}

	public StringProperty repeatStyleProperty() {
		return repeatStyle;
	}

	public void setRepeatStyle(String repeatStyle) {
		this.repeatStyle.set(repeatStyle);
	}

	public String getMuteStyle() {
		return muteStyle.get();
	}

	public StringProperty muteStyleProperty() {
		return muteStyle;
	}

	public void setMuteStyle(String muteStyle) {
		this.muteStyle.set(muteStyle);
	}

	public String getPlayButtonText() {
		return playButtonText.get();
	}

	public StringProperty playButtonTextProperty() {
		return playButtonText;
	}

	public void setPlayButtonText(String playButtonText) {
		this.playButtonText.set(playButtonText);
	}

	public boolean isCountTimeCalled() {
		return countTimeCalled;
	}

	public void setCountTimeCalled(boolean bool) {
		this.countTimeCalled = bool;
	}
}
