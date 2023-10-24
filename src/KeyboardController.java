
// Verwaltet Inputs, ruft Methoden des Players auf

import java.util.Scanner;

public class KeyboardController {
	
	// Eingaben
	public static final String PLAY = "play";
	public static final String PAUSE = "pause";
	public static final String VOLUME = "volume";
	public static final String QUIT = "quit";
	public static final String TEST = "test";
	
	
	// startet MP3-Player
	public static void main(String[] args) {
		try {
			start();
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void start() {
		
		MP3Player mp3Player = new MP3Player();
		
		Scanner s = new Scanner(System.in);
		
		boolean quit = false;

		// solange Player nicht beendet
		while(!quit) {
			String input = s.nextLine();
			
			String[] splitted = input.split(" ");
			
			switch (splitted[0].toLowerCase()) {
			// break funktioniert nicht
				case PLAY:
					/*
					 * Check for Filename
					 *  ./02_Drei_Worte.mp3
					 *  ./01_Bring_Mich_Nach_Hause.mp3
					*/ 
					if(splitted.length > 1) {
						mp3Player.play(splitted[1]);
						break;
					}
					else {
						/*
						 *  hier ggf. try-catch, wenn Funktion so gedacht ist, dass play nur möglich ist, wenn Titel bereits gestartet
						 *  passt das try-catch dann so?
						 *  
						 *  try {
						 *  	mp3Player.play();
						 *  } catch (NullPointerException e) {
						 *  	System.out.println("Bitte zuerst einen Song auswählen");
						 *  }
						 */
						mp3Player.play();
						break;
					}
					
				case PAUSE:
					mp3Player.pause();
					break;
					
				case VOLUME: // float
					if(splitted.length > 1) {
						try {
							float volume = Float.parseFloat(splitted[1]);
							if (volume >= 0 && volume <= 1) {
								mp3Player.volume(volume);
							}
							else {
								System.out.println("Bitte eine Zahl zwischen 0 und 1 angeben");
							}
						} catch(NumberFormatException e) {
							System.out.println("Bitte eine Gültige Zahl eingeben");
						}
					}
					else {
						System.out.println("Bitte eine Lautstärke angeben");
					}
					break;
					
				case QUIT:
					quit = true;
					System.out.println("quit");
					break;
					
				case TEST:
					mp3Player.test();
					
				default:
					System.out.println("Unbekannter Befehl");
					break;
			}
		} 
		s.close();
	}

}
