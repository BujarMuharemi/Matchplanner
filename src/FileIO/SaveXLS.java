package FileIO;

import java.util.List;

import model.Spiel;
import model.TabelleSpielTage;

public class SaveXLS {
String path;
List<Spiel> daten;
	
	public SaveXLS(String path, TabelleSpielTage tabelle) {
		this.path = path;
		this.daten = tabelle.getSpielTageOutput();
		
		for (Spiel spiel : daten) {
			System.out.println(spiel.getM1().getName().toString());
		}
	}
	
	public void saveXLS() {
		
	}
}
