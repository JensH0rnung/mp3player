# MP3Player 

Diese App ist das 1. von 2 Projekten, die im Rahmen des Moduls 'Entwicklung interaktiver Benutzeroberflächen' entstanden sind.
Insbesondere diente dieses Projekt als Basis für die Entwickung von Apps mit JavaFX.
Hierbei sollten folgende
Vorlesungsinhalte miteinfließen:
- Package-Struktur unter Beachtung der Schichtenarchitektur
- MVC
- View-Komponenten & Layout-Skinning
- Controller
- Nebenläufigkeit mit Threading
- Beans & Binding
- Bedienelemente, insbesondere ListView

## Funktionalität

Allgemein funktioniert die Soundwiedergabe mithilfe des Minim-Players, der in unserem Fall in vereinfachter Form zur Verfügung gestellt wurde (Implementierung in der '*eibo_simpleminim_new.jar*').

Der Player beinhaltet die üblichen PlayerControls wie bspw. Play, Skip oder Shuffle.
Darüber hinaus sollten 2 Views mit versch. Komponenten erstellt werden:
1. Aktueller Song - Cover, PlayerControls, Songinfos, Gesamtzeit & aktuelle Zeit
2. Playlist - Liste aller Songs in einem Verzeichnis, PlayerControls

Für die Umsetzung obiger Funktionen lädt der PlaylistManager alle mp3-Dateien aus einem Verzeichnis, erstellt Song-Objekte, fügt diese einer Playlist hinzu und zeigt diese Playlist in einem ListView an.
Nun kann der User entscheiden, ob er einen zufälligen Song spielen möchte (Play-Button) oder ob er einen konkreten Song der Playlist abspielen möchte.

## Voraussetzungen hinsichtlich der Architektur

- Wechsel zwischen den Ansichten
- Modulare MVCs
- Beachtung der Schichtenarchitektur
- Trennung der Style-Informationen von Programm-Code
- skalierbares Layout

## Geplante Features

- mehrere Playlists erstellen & auswählen
- Animationen einfügen
