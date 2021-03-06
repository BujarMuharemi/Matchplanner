package FileIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Ergebnisse;
import model.Mannschaften;
import model.Spiel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class OpenXLSX {
	// Klassenvariable
	String path;
	List<Spiel> daten = new ArrayList<>();
	List<String> team1, team2, erg;
	List<Date> datum;

	public OpenXLSX(String openpath) {
		// Konstruktor mit Dateipfad übergabe
		this.path = openpath;
		System.out.println(this.path);
		readXLSX();
	}

	public void readXLSX() {
		// readXLSX ließt die Datentypen XLSX im pfad 
		try {
			Workbook workbook = WorkbookFactory.create(new File(this.path));
			Sheet sheet = workbook.getSheetAt(0);
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row row = sheet.getRow(i);

				Spiel spiel = new Spiel(new Mannschaften(row.getCell(0).toString()),
						new Mannschaften(row.getCell(1).toString()), new Ergebnisse(row.getCell(2).toString()),
						new Date());
				spiel.fromStringtoDate(row.getCell(3).toString());
				daten.add(spiel);
			}
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Spiel> getSpieltabelle() {
		return this.daten;
	}
}
