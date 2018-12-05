package model;

import javax.swing.table.AbstractTableModel;

public class NewViewTabelle extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private int size = 1;
	String[] columNames = { "ID", "Teams" };

	// Gui mainGUI = new Gui();

	private Mannschaften[] teams; // Speichert die teamName

	// public void sendTeams() {
	// mainGUI.setMan(this.teams);
	//// for (int i = 0; i < teams.length; i++) {
	//// System.out.println("\t" + teams[i].getName());
	//// }
	// mainGUI.b.setN(teams.length);
	// }

	public Mannschaften[] getTeams() {
		return teams;
	}

	public int getSpieltage() {
//		System.out.println(teams.length);
		int n = (teams != null) ? teams.length : 4;
		return (n * (n - 1)) / (n / 2);
	}

	private void initTeams(int size) {
		teams = new Mannschaften[size];
		for (int i = 0; i < teams.length; i++) {
			teams[i] = new Mannschaften("<bitte aendern>");
		}
		// sendTeams();
	}

	public NewViewTabelle(int size) {
		this.size = size;
		initTeams(size);
	}

	public void addSize(int n) {
		this.size = n;
		initTeams(n);
		fireTableStructureChanged();
	}

	public int getRowCount() {
		return size;
	}

	public Object getValueAt(int rowIndex, int ColumIndex) {
		if (ColumIndex == 0) {
			return rowIndex + 1;
		} else {
			String name = (teams[rowIndex] != null) ? teams[rowIndex].getName() : "<bitte aendern>";
			return name;
		}
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (teams != null) {
			teams[rowIndex] = new Mannschaften(aValue.toString());
			// System.out.println(rowIndex + ":" + teams[rowIndex].getName());
		}
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (columnIndex == 0) ? false : true;
	}

	@Override
	public int getColumnCount() {
		return columNames.length;
	}

	@Override
	public String getColumnName(int index) {
		return columNames[index];
	}

}