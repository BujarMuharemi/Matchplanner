package FileIO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import model.Spiel;

public class OpenXLS {

	String path;
	List<Spiel> daten;

	public OpenXLS(String openpath) {
		this.path = openpath;
		
		try {
			//TODO:Daten auslesen aus den XLS datei!
			FileInputStream fis = new FileInputStream(path);
			XSSLWorkbook wb 
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
	}
}
