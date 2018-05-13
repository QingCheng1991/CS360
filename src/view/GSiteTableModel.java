package view;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class GSiteTableModel extends AbstractTableModel{
	private String[] columnNames = {"No.", "Site Name"};
	private ArrayList<GCollectionSite> data = new ArrayList<GCollectionSite>();
	
	public void addGSite(GCollectionSite gSite) {
		data.add(gSite);
		//fireTableDataChanged();
		//System.out.println("dataSize is " + data.size());
		fireTableRowsInserted(data.size() -1 , data.size() -1);
	}
	
	public void removeGSite(GCollectionSite gSite) {
		if (data.size() == 0) return;
		int index = getIndexOf(gSite);
		data.remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void editGSite(GCollectionSite gSite) {
		int index = getIndexOf(gSite);
		if (index == -1) return;
		data.set(index, gSite);
	}
	
	int getIndexOf(GCollectionSite gSite) {
		for (int i = 0; i < data.size(); i++) {
			if ( data.get(i).getSiteNumber() == gSite.getSiteNumber()) {
				return i;
			}
		}
		return -1;
	}
	
	public GCollectionSite getSiteAtIndex(int index) {
		if (index >= 0) 
			return data.get(index);
		return null;
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (data.size() == 0) return null;
		//System.out.println("columnIndex " + columnIndex);
		if (columnIndex == 0) return data.get(rowIndex).getSiteNumber();
		if (columnIndex == 1) return data.get(rowIndex).getSiteName();
		return null;
	}
	
	public Class getColumnClass(int c) {
		if (c == 0) return Integer.class;
		if (c == 1) return String.class;
        return getValueAt(0, c).getClass();
    }
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public String getColumnName(int col) {
        return columnNames[col];
    }
}
