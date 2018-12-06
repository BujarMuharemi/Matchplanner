package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TabelleSpielTage extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<Spiel> spielTage = new ArrayList<>();
	private List<Spiel> spielTageOutput = spielTage;
	private Mannschaften[] teams;

	String[] columNames = { "Team1", "Team2", "Ergebnis", "Datum" };
	
	int spielProTag = 0;

	public TabelleSpielTage(Mannschaften[] teams) {
		this.teams = teams;

//		System.out.println("TabelleSpielTage");

		if (teams != null) {
//			for (int i = 0; i < this.teams.length; i++) {
//				System.out.println("Aus SpielTagTabelle\t" + i + ":" + this.teams[i].getName());
//
//				addRow();
//			}
//			System.out.println("Length recived: " + this.teams.length);
		}
		fireTableStructureChanged();
	}

	public void showSpieltag(int n) {
		spielTage=spielTageOutput;
		System.out.println("N:"+n);
		List<Spiel> temp = new ArrayList<>();
		for (int i = 0; i < spielProTag; i++) {
			temp.add(spielTageOutput.get(n+i));
		}
		spielTageOutput = temp;
		fireTableStructureChanged();
//		spielTageOutput=spielTage;
	}

//	public void addRow() {
//		spielTage
//				.add(new Spiel(new Mannschaften("asdf"), new Mannschaften("qwertz"), new Ergebnisse(1, 0), new Date()));
//		spielTageOutput = spielTage;
//		fireTableStructureChanged();
//	}

	public void addRow(Mannschaften m1, Mannschaften m2, Ergebnisse e1, Date d) {
		spielTageOutput.add(new Spiel(m1, m2, e1, d));
		fireTableStructureChanged();
	}

	public void addTeam(Mannschaften[] teams) {
		spielTageOutput.removeAll(spielTage);

		this.teams = teams;
		for (int i = 0; i < teams.length; i++) {
//			addRow();
		}
		fireTableStructureChanged();
	}
	
	public void updateSpieltage() {
		spielTageOutput=spielTage;
	}

	public void createSpieltage() {
		int spielTageInt = teams.length;
		spielTageInt = (spielTageInt * (spielTageInt - 1)) / (spielTageInt / 2);

		int spieleGesamt = (teams.length / 2) * spielTageInt;
		spielProTag = spieleGesamt / spielTageInt;

		System.out.println("■■■■■■■■■■■■\nSpielTage: " + spielTageInt + "\tSpielegesamt: " + spieleGesamt
				+ "\tSPieleProTag: " + spielProTag);
		// hinspiel
		for (int j = 0; j < teams.length - 1; j++) {

			for (int k = j + 1; k < teams.length; k++) {
				System.out.println((j + 1) + ":" + (k + 1));		
				
				addRow(teams[j], teams[k], new Ergebnisse(0, 0), new Date());
			}

		}
		System.out.println("■");

		// ruckspiele
		for (int j = 0; j < teams.length - 1; j++) {

			for (int k = j + 1; k < teams.length; k++) {
				System.out.println((k + 1) + ":" + (j + 1));
				addRow(teams[j], teams[k], new Ergebnisse(0, 0), new Date());
			}

		}

		fireTableStructureChanged();
		spielTage=spielTageOutput;
		System.out.println("Length" + spielTageOutput.size());
	}

	@Override
	public int getColumnCount() {
		return columNames.length;
	}

	@Override
	public int getRowCount() {
		return spielTageOutput.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int ColumIndex) {
//		return teams[rowIndex].getName();
		String n = "";
		if (ColumIndex == 0) {
			n = spielTageOutput.get(rowIndex).getM1().getName();
		} else if (ColumIndex == 1) {
			n = spielTageOutput.get(rowIndex).getM2().getName();
		} else if (ColumIndex == 3) {
			n = spielTageOutput.get(rowIndex).getDate().toString();
		} else {
			n = spielTageOutput.get(rowIndex).getErg().toString();
		}
		return n;
	}

	@Override
	public String getColumnName(int index) {
		return columNames[index];
	}

}