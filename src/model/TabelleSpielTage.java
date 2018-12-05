package model;

import javax.swing.table.AbstractTableModel;

public class TabelleSpielTage extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private List<Spiel> spielTage = new ArrayList<>();
	private Mannschaften[] teams;
	

	String[] columNames = { "Team1", "Team2", "Ergebnis", "Datum" };

	public TabelleSpielTage(Mannschaften[] teams) {
		this.teams = teams;

		if (teams != null) {
			for (int i = 0; i < this.teams.length; i++) {
				System.out.println("Aus SpielTagTabelle\t" + i + ":" + this.teams[i].getName());

			}
		}
		
	}
	
	public void addTeam(Mannschaften[] teams) {
		System.out.println("addTeam");
		this.teams=teams;
		fireTableStructureChanged();
	}

	@Override
	public int getColumnCount() {
		return columNames.length;
	}

	@Override
	public int getRowCount() {
		return teams.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int ColumIndex) {		
		return teams[rowIndex].getName();
	}

	@Override
	public String getColumnName(int index) {
		return columNames[index];
	}

	public void addSize(int i) {
		
		
	}

}
