package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class TabelleSpielTage extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<Spiel> spielTage = new ArrayList<>();
	private List<Spiel> spielTageOutput = spielTage;
	public List<Spiel> getSpielTageOutput() {
		return spielTageOutput;
	}

	private Mannschaften[] teams;

	String[] columNames = { "Team1", "Team2", "Ergebnis", "Datum" };

	int spieleProTag = 0;

	private boolean editTeams = false;
	private boolean editDates = false;

	int count = 0;

	public TabelleSpielTage(Mannschaften[] teams) {
		this.teams = teams;

		// System.out.println("TabelleSpielTage");

		if (teams != null) {
			// for (int i = 0; i < this.teams.length; i++) {
			// System.out.println("Aus SpielTagTabelle\t" + i + ":" +
			// this.teams[i].getName());
			//
			// addRow();
			// }
			// System.out.println("Length recived: " + this.teams.length);
		}
		fireTableStructureChanged();
	}

	public void editTeams() {
		editTeams = true;
		editDates = false;
	}

	public void editDates() {
		editDates = true;
		editTeams = false;
	}

	public boolean getEditTeam() {
		return editTeams;
	}

	public boolean getEditDates() {
		return editDates;
	}

	public void setEditTeam(boolean b) {
		editTeams = b;
	}

	public void setEditDates(boolean b) {
		editDates = b;
	}

	public void resetSpielTage() {
		for (Spiel spiel : spielTage) {
			spiel = null;
		}
	}

	public void showSpieltag(int n) {
		spielTage = spielTageOutput;
		List<Spiel> temp = new ArrayList<>();

		count = n * spieleProTag;

		for (int i = 0; i < spieleProTag; i++) {
			temp.add(spielTageOutput.get(count + i));
		}
		spielTageOutput = temp;
		fireTableStructureChanged();
	}

	public void addRow(Mannschaften m1, Mannschaften m2, Ergebnisse e1, Date d) {
		spielTageOutput.add(new Spiel(m1, m2, e1, d));
		fireTableStructureChanged();
	}

	public void addTeam(Mannschaften[] teams) {
		spielTageOutput.removeAll(spielTage);

		this.teams = teams;
		for (int i = 0; i < teams.length; i++) {
			// addRow();
		}
		fireTableStructureChanged();
	}

	public void updateSpieltage() {
		spielTageOutput = spielTage;
	}

	public void createSpieltage() {
		int spielTageInt = teams.length;	
		spielTageInt = (spielTageInt * (spielTageInt - 1)) / (spielTageInt / 2);
		
		int spieleGesamt = (teams.length / 2) * spielTageInt;
		spieleProTag = spieleGesamt / spielTageInt;

		// Für das Zählen der Tage
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int counter = 0;

		// Hinspiele
		for (int j = 0; j < teams.length - 1; j++) {

			for (int k = j + 1; k < teams.length; k++) {
				// System.out.println((j + 1) + ":" + (k + 1));

				if (counter >= spieleProTag) {
					cal.add(Calendar.DATE, 1);
					date = cal.getTime();
					counter = 0;
				}
				counter++;

				addRow(teams[j], teams[k], new Ergebnisse(0, 0), date);
			}
		}

		// ruckspiele
		for (int j = 0; j < teams.length - 1; j++) {

			for (int k = j + 1; k < teams.length; k++) {
				// System.out.println((k + 1) + ":" + (j + 1));

				if (counter >= spieleProTag) {
					cal.add(Calendar.DATE, 1);
					date = cal.getTime();
					counter = 0;
				}
				counter++;

				addRow(teams[k], teams[j], new Ergebnisse(0, 0), date);
			}

		}

		fireTableStructureChanged();
		spielTage = spielTageOutput;
		// System.out.println("Length" + spielTageOutput.size());
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
		// return teams[rowIndex].getName();
		String n = "";
		if (ColumIndex == 0) {
			n = spielTageOutput.get(rowIndex).getM1().getName();
		} else if (ColumIndex == 1) {
			n = spielTageOutput.get(rowIndex).getM2().getName();
		} else if (ColumIndex == 3) {
			n = spielTageOutput.get(rowIndex).toStringDate();
		} else {
			n = spielTageOutput.get(rowIndex).getErg().toString();
		}
		return n;
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (spielTageOutput != null) {
			if (columnIndex == 0) {
				spielTageOutput.get(rowIndex).setM1(new Mannschaften(aValue.toString()));
				editTeams = true;

			} else if (columnIndex == 1) {
				spielTageOutput.get(rowIndex).setM2(new Mannschaften(aValue.toString()));
				editTeams = true;

			} else if (columnIndex == 2) {
				Ergebnisse ergbe = new Ergebnisse(0, 0); // TODO#3: string auf splitten und ergebniss in ints
				spielTageOutput.get(rowIndex).setErg(ergbe);

			} else if (columnIndex == 3) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
				String dateInString = aValue.toString();
				Date d = null;

				try {
					d = sdf.parse(dateInString);
					spielTageOutput.get(rowIndex).setDate(d);
					editDates = true;
				} catch (ParseException e) {
					// e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Richtiges Datumsformat nutzen !");
				}

			} else {
				editTeams = false;
				editDates = false;
			}

			// System.out.println(rowIndex + ":" + teams[rowIndex].getName());
		}
	}

	@Override
	public String getColumnName(int index) {
		return columNames[index];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		boolean a = false;
		if (editDates && columnIndex == 3) {
			a = true;
		} else if (editTeams && (columnIndex == 0 || columnIndex == 1)) {
			a = true;
		}
		return a;
	}

}
