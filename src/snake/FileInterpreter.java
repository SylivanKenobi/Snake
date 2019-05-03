package snake;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * Klasse zum beschreiben und auslesen von Dateien.
 * @author Sylvain Gilgen
 *
 */
public class FileInterpreter {
	static File file = new File("text.txt");
	static File fileManual = new File("manual.txt");

	/**
	 * Daten aus der Speicherdatei auslesen und das Resultat als String zurückgeben.
	 * 
	 * @return String input
	 * @throws FileNotFoundException
	 */

	public static String scan() throws FileNotFoundException {
		TreeMap<Integer, String> scores = new TreeMap<Integer, String>(Collections.reverseOrder());
		String input = "";
		Scanner scan = new Scanner(file);
		String spieler = "";
		int score;
		int maxAusgabe = 0;		
		while (scan.hasNext()) {
			spieler = scan.next();
			score = scan.nextInt();
			scores.put(score, spieler);
		}
		scan.close();

		for (Entry<Integer, String> entry : scores.entrySet()) {
			score = entry.getKey();
			spieler = entry.getValue();
			input += spieler + " " + score + "\n";
			maxAusgabe++;
			if (maxAusgabe == 5) {
				break;
			}
		}
		return input;
	}

	/**
	 * Punkte des Spielers in der Datei abspeichern.
	 * 
	 * @param name   des Spielers
	 * @param punkte des Spielers
	 * @return boolean ob das drucken funktioniert hat
	 */
	public static boolean print(String name, int punkte) {
		FileWriter fw;
		PrintWriter writer;
		try {
			fw = new FileWriter(file, true);
			writer = new PrintWriter(fw);

		} catch (Exception i) {
			System.out.println("bitch!!!");
			return false;
		}
		writer.println(name + " " + punkte);
		writer.close();
		return true;

	}
	
	/**
	 * Anleitung aus Datei auslesen
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String scanManual() throws FileNotFoundException {
		Scanner scanner = new Scanner(fileManual);
		String manual = "";
		while (scanner.hasNextLine()) {
			manual += scanner.nextLine()+"\n";
		}
		scanner.close();
		return manual;
	}
}
