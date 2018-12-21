package FileIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import model.Spiel;
import model.TabelleSpielTage;

public class SaveCSV {

	String path;
	List<Spiel> daten = new ArrayList<>();

	public SaveCSV(String path, TabelleSpielTage tabelle) {
		// Konstruktor mit Speicherpfad übergabe 
		this.path = path;
		this.daten = tabelle.getSpielTageOutput();
		csvWriter();
	}

	public void csvWriter() {
		// readXLSX ließt die Datentypen XLSX im pfad 
		try {
			PrintWriter pw = new PrintWriter(new File(this.path));
			StringBuilder sb = new StringBuilder();
			// Header wird erstellt
			sb.append("Team1");
			sb.append(';');
			sb.append("Team2");
			sb.append(';');
			sb.append("Ergebnis");
			sb.append(';');
			sb.append("Datum");
			sb.append(';');
			sb.append('\n');
			// Daten werden aus der Tabelle zu CSV hinzugefügt
			for (Spiel spiel : daten) {
				sb.append(spiel.getM1().getName());
				sb.append(';');
				sb.append(spiel.getM2().getName());
				sb.append(';');
				sb.append(spiel.getErg().getErg1()+ ":" +spiel.getErg().getErg2());
				sb.append(';');
				sb.append(spiel.toStringDate());
				sb.append(';');
				sb.append('\n');
			}
			pw.write(sb.toString());
			pw.close();
			System.out.println("gespeichert");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
