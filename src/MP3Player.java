
// Funktionen des Players


import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

public class MP3Player {
	
	private SimpleMinim minim;
	private SimpleAudioPlayer audioPlayer;
	
	// Constructor?
	MP3Player(){
		minim = new SimpleMinim();
	}

	void test() {
		System.out.println(audioPlayer.getVolume());
	}
	
	/**
	 * 
	 * @param fileName - Name des Songs, der gespielt werden soll
	 * 
	 * 	./02_Drei_Worte.mp3
	 * ./01_Bring_Mich_Nach_Hause.mp3
	 */
	void play(String fileName) {
		audioPlayer = minim.loadMP3File(fileName);
		play();
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
	
	// [0, 1]
	void volume(float volume) {
		/*
		 *  Hinweis auf Folie 4 beachten
		 *  Wert in decibel angeben
		 */
		audioPlayer.setGain(volume);
	}
}
