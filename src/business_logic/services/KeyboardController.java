package business_logic.services;
/*
Verwaltet Inputs, ruft Methoden des Players auf
 */

import java.io.*;
import java.util.Scanner;

public class KeyboardController {
	
	// Eingaben
	public static final String PLAY = "play";
	public static final String PAUSE = "pause";
	public static final String VOLUME = "volume";
	public static final String QUIT = "quit";
	public static final String SKIP = "skip";
	public static final String SKIPBACK = "skipback";
	public static final String REPEAT = "repeat";
	public static final String SHUFFLE = "shuffle";

	public static final String ON = "on";
	public static final String OFF = "off";

	public static final String TEST = "test";

	
	// startet MP3-Player
	public static void main(String[] args) {
		start();
	}

	public static void start() {

		// Test



		MP3Player player = new MP3Player();
		PlaylistManager manager = new PlaylistManager();

		boolean shuffleOn = player.isOnShuffle();
		boolean repeatOn = player.isOnRepeat();

		Scanner	scanner = new Scanner(System.in);
		String input;
		String[] commands;

		boolean quit = false;

		// solange Player nicht beendet
		do {
			input = scanner.nextLine();

			commands = input.split(" ");

			switch (commands[0].toLowerCase()) {

				// Test
				case "test":
					player.test();
					break;
				case "p":
					player.test2();
					break;
				case PLAY:
					/*
					 * Check for Filename, bspw.
					 *  ./music/02_Drei_Worte.mp3
					 *  ./music/01_Bring_Mich_Nach_Hause.mp3
					 */
					if (commands.length > 1) {
						player.play(commands[1]);
					} else {
						player.play();
					}
					break;
				case PAUSE:
					player.pause();
					break;
				case VOLUME:
					if (commands.length > 1) {
						try {
							float volume = Float.parseFloat(commands[1]);
							if (volume >= 0 && volume <= 1) {
								player.volume(volume);
							} else {
								System.out.println("Bitte eine Zahl zwischen 0 und 1 angeben");
							}
						} catch (NumberFormatException e) {
							System.out.println("Bitte eine Gültige Zahl eingeben");
						}
					} else {
						System.out.println("Bitte eine Lautstärke angeben");
					}
					break;
				case QUIT:
					quit = true;
					System.out.println("MP3Player wird geschlossen... \n");
					break;
				case SKIP:
					player.skip();
					break;
				case SKIPBACK:
					player.skipBack();
					break;
				case SHUFFLE:
					if (commands.length > 1) {
						String onoffInput = commands[1];

						if(onoffInput.equals(ON) && shuffleOn) {
							System.out.println("Shuffle ist bereits aktiviert");
						} else if (onoffInput.equals(OFF) && !shuffleOn) {
							System.out.println("Shuffle ist bereits deaktiviert");
						} else if (onoffInput.equals(ON)) {
							player.shuffle(true);
							shuffleOn = true;
							System.out.println("Shuffle ON");
						} else if (onoffInput.equals(OFF)) {
							player.shuffle(false);
							shuffleOn = false;
							System.out.println("Shuffle OFF");
						} else {
							System.out.println("Bitte 'shuffle on' oder 'shuffle off' eingeben");
						}
					} else {
						System.out.println("Bitte 'shuffle on' oder 'shuffle off' eingeben");
					}
					break;
				case REPEAT:
					if (commands.length > 1) {
						String onoffInput = commands[1];

						if(onoffInput.equals(ON) && repeatOn) {
							System.out.println("Repeat ist bereits aktiviert");
						} else if (onoffInput.equals(OFF) && !repeatOn) {
							System.out.println("Repeat ist bereits deaktiviert");
						} else if (onoffInput.equals(ON)) {
							player.repeat(true);
							repeatOn = true;
							System.out.println("Repeat ON");
						} else if (onoffInput.equals(OFF)) {
							player.repeat(false);
							repeatOn = false;
							System.out.println("Repeat OFF");
						} else {
							System.out.println("Bitte 'repeat on' oder 'repeat off' eingeben");
						}
					} else {
						System.out.println("Bitte 'repeat on' oder 'repeat off' eingeben");
					}
					break;
				default:
					System.out.println("Unbekannter Befehl");
					break;
			}
		} while (!quit);
		scanner.close();
	}
}
