package model;

import javax.swing.table.AbstractTableModel;

public class NewViewTabelle extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private int size = 1;
	String[] columNames = { "ID", "Teams" };

	private Mannschaften[] teams; // Speichert die teamName

	public Mannschaften[] getTeams() {
		return teams;
	}

	public int getSpieltage() {
		int n = (teams != null) ? teams.length : 4;
		return (n * (n - 1)) / (n / 2);
	}

	private void initTeams(int size) {
		teams = new Mannschaften[size];
		for (int i = 0; i < teams.length; i++) {
			teams[i] = new Mannschaften("<bitte aendern>");
		}
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
			String name = (teams[rowIndex] != null) ? teams[rowIndex].getName() : "<bitte ändern>";
			return name;
		}
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (teams != null) {
			teams[rowIndex] = new Mannschaften(aValue.toString());
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