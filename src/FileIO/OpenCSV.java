package FileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Mannschaften;

public class OpenCSV {
	String path;
	private Mannschaften[] teams;
	private List<Mannschaften> teamListe = new ArrayList<>();
	
	public OpenCSV(File selectedFile) {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
		
		try {
			br = new BufferedReader(new FileReader(selectedFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			while ((line = br.readLine()) != null) {
				String[] elements = line.split(cvsSplitBy);
				
				for (int i = 1; i < elements.length; i++) {
					if(i==4) {
						teamListe.add(new Mannschaften(elements[i]));
					}
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Mannschaften[] returnTeams() {
		teams= new Mannschaften[teamListe.size()];
		
		for (int i = 0; i < teams.length; i++) {
			teams[i]=teamListe.get(i);
		}
		return teams;
	}
}
