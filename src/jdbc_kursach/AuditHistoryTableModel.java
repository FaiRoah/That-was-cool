package jdbc_kursach;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import gamesearch.core.AuditHistory;
import gamesearch.core.Game;

public class AuditHistoryTableModel extends AbstractTableModel{
	private static final int ID_COL = 0;
	private static final int USERID_COL = 1;
	private static final int GAMEID_COL = 2;
	private static final int ACTION_COL = 3;
	private static final int ACTIONDATE_COL = 4;
	static final int OBJECT_COL = -1;

	private String[] columnNames = { "id записи", "id пользователя", "id измененной игры", "действие", "дата действия"};
	
	private List<AuditHistory> histories;
	
	public AuditHistoryTableModel(List<AuditHistory> theHistories) {
		histories = theHistories;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public int getRowCount() {
		return histories.size();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		AuditHistory tempHistory= histories.get(row);
		
		switch(col) {
			case OBJECT_COL:
				return tempHistory;
			case ID_COL:
				return tempHistory.getId();
			case USERID_COL:  
				return tempHistory.getUser_id();
			case GAMEID_COL:  
				return tempHistory.getGame_id();
			case ACTION_COL: 
				return tempHistory.getAction();
			case ACTIONDATE_COL:
				return tempHistory.getAction_date();
			default:
				return tempHistory.getAction_date();
		}
	}
	
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

}
