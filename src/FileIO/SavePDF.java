package FileIO;

import java.util.ArrayList;
import java.util.List;
import model.Spiel;
import model.TabelleSpielTage;

public class SavePDF {

	String path;
	List<Spiel> daten = new ArrayList<>();
	public SavePDF(String path, TabelleSpielTage tabelle) {
		this.path = path;

		writePDF(tabelle);
	}

	public void writePDF(TabelleSpielTage tabelle) {
	    
	}
}
