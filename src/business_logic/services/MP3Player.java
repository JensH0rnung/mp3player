package business_logic.services;
import business_logic.data.Playlist;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

/**
 Funktionen des Players:
 - play [filename] - verwendet SimpleAudioPlayer zum Abspielen von mp3-Dateien
 - pause - pausiert aktuelle Wiedergabe
 - volume [0-1] - ändert die WiedergabeLautstärke
 - ... - spielt eine Playlist ab
 */
public class MP3Player {

	public static final String song = "./music/Empire_State_Of_Mind.mp3";
	
	private SimpleMinim minim;
	private SimpleAudioPlayer audioPlayer;
	private PlaylistManager manager;
	private Playlist actPlaylist;
	private boolean shuffle;
	private boolean repeat;

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
		this.actPlaylist = manager.getPlaylist("./music/test.m3u");
		// Standardwerte
		this.shuffle = false;
		this.repeat = false;
	}

	void test() {
//		System.out.println("Shuffle - " + this.shuffle);
//		System.out.println("Repeat - " + this.repeat);
		System.out.println(audioPlayer.length());
		audioPlayer.skip(264178);
//		System.out.println(this.actPlaylist.getName());
//		System.out.println(this.actPlaylist.getSongs());
//		System.out.println(audioPlayer.length());
	}

	void test2() {
		if(audioPlayer == null) {
			audioPlayer = minim.loadMP3File(song);
			play();
			pause();
		}
		// den anderen Thread stoppen und neuen mit gegebenem Song starten
		else {
			System.out.println("Es wird bereits ein Song gespielt");
		}
	}
	
	/**
	 * 
	 * @param fileName - Name des Songs, der gespielt werden soll
	 * 
	 * ./02_Drei_Worte.mp3
	 * ./01_Bring_Mich_Nach_Hause.mp3
	 */
	void play(String fileName) {
		if(audioPlayer == null) {
			audioPlayer = minim.loadMP3File(fileName);
			play();
		}
		// den anderen Thread stoppen und neuen mit gegebenem Song starten
		else {
			System.out.println("Es wird bereits in Song gespielt");
		}
	}
	
	/**
	 * Play funktioniert nur, wenn vorher Song ausgewählt wurde
	 * Design-Entscheidung, bei Spotify gibt's die Funktion am Handy nicht wenn kein Song ausgewählt wurde
	 * Bei Spotify-Desktop ist der zuletzt gespielte Song ausgewählt
	 */
	void play() {
		try {
			audioPlayer.play();
		} catch (NullPointerException e) {
			System.out.println("Bitte den Song zum Abspielen auswählen - play filename");
		}
	}
	
	void pause() {
		audioPlayer.pause();
	}

	void volume(float volume) {
		// Wert in Dezibel umrechnen, da setGain mit Decibel arbeitet
		float decibel = (float) (20* Math.log10(volume));

		audioPlayer.setGain(decibel);
	}

	void setActPlaylist(Playlist actPlaylist) {
		/*
		 wie setzt man hier ne Playlist, die zuvor erstellt wurde?
		 */
	}

	void skip(){
		/*
		 nächsten Song aus Playlist wählen
		 Shuffle berücksichtigen
		 */
		System.out.println(actPlaylist.getSongs());

		// int muss größer sein, als length
//		int songLength = audioPlayer.length();
//		audioPlayer.skip(songLength + 1);
	}
	void skipBack(){}
	void shuffle(boolean on) {
        this.shuffle = on;
	}
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
