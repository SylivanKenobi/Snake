package snake;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class FileInterpreter {
    static File file = new File("assets/text.txt");
    static File fileManual = new File("assets/manual.txt");

    public static String scan() throws FileNotFoundException {
        TreeMap<Integer, String> scores = new TreeMap<Integer, String>(Collections.reverseOrder());
        String input = "";
        Scanner scanner = new Scanner(file);
        String spieler = "";
        int score;
        int maxAusgabe = 0;
        while (scanner.hasNext()) {
            spieler = scanner.next();
            score = scanner.nextInt();
            scores.put(score, spieler);
        }
        scanner.close();

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

    public static boolean print(String name, int punkte) {
        FileWriter fw;
        PrintWriter writer;
        try {
            fw = new FileWriter(file, true);
            writer = new PrintWriter(fw);

        } catch (Exception i) {
            return false;
        }
        writer.println(name + " " + punkte);
        writer.close();
        return true;

    }

    public static String scanManual() throws FileNotFoundException {
        Scanner scanner = new Scanner(fileManual, "ISO_8859_1");
        StringBuilder manual = new StringBuilder();
        while (scanner.hasNextLine()) {
            manual.append(scanner.nextLine()).append("\n");
        }
        scanner.close();
        return manual.toString();
    }
}
