package model;
import javax.swing.table.AbstractTableModel;

public class Tabelle extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	String[] columNames = { "SpielTag" };

	private int size = 6;

	public Tabelle(int size) {
		this.size = size;
	}

	public void addSize(int n) {
		this.size = n;	
		System.out.println("addSize: "+this.size);
		fireTableStructureChanged();
		
	}
	
	public void setSize(int n) {		
		this.size = n;		
//		System.out.println("setSize: "+this.size);
		fireTableStructureChanged();	
	}
	
	@Override
	public int getRowCount() {			
		return this.size;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int ColumIndex) {
		return rowIndex += 1;
	}
	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public String getColumnName(int index) {
		return columNames[index];
	}

}