package FileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import model.Ergebnisse;
import model.Mannschaften;
import model.Spiel;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class OpenXLS {
	//Klassenvariable
	String path;
	List<Spiel> daten = new ArrayList<>();
	List<String> team1, team2, erg;
	List<Date> datum;

	public OpenXLS(String openpath) {
		// Konstruktor mit Dateipfad übergabe
		this.path = openpath;
		System.out.println(this.path);
		readXLS();
	}
	//  readXLS ließt die XLS datei im pfad
	public void readXLS() {
		try {
			Workbook workbook = WorkbookFactory.create(new File(this.path));
			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter dataFormatter = new DataFormatter();
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
