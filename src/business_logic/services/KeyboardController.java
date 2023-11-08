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
				case PLAY:
					/*
					 * Check for Filename, bspw.
					 *  ./music/02_Drei_Worte.mp3
					 *  ./music/01_Bring_Mich_Nach_Hause.mp3
					 */
					if (commands.length > 1) {
						player.play(commands[1]);
						break;
					} else {
						player.play();
						break;
					}
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
					boolean shuffleOn = player.isOnShuffle();

					if (commands.length > 1) {
						String onoff = commands[1];

						if(onoff.equals(ON) && shuffleOn) {
							System.out.println("Shuffle ist bereits aktiviert");
						} else if (onoff.equals(ON)) {
							player.shuffle(true);
							System.out.println("Shuffle ON");
						} else if (onoff.equals(OFF) && shuffleOn) {
							player.shuffle(false);
							System.out.println("Shuffle OFF");
						} else if (onoff.equals(OFF)) {
							System.out.println("Shuffle ist bereits deaktiviert");
						}
					} else {
						System.out.println("Bitte 'shuffle on' oder 'shuffle off' eingeben");
					}
					break;
				case REPEAT:
					// das gleiche wie bei shuffle
					break;

				default:
					System.out.println("Unbekannter Befehl");
					break;
			}
		} while (!quit);
		scanner.close();
	}
}
