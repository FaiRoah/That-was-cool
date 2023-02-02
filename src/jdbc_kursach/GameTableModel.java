package jdbc_kursach;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import gamesearch.core.Game;

class GameTableModel extends AbstractTableModel{
	
	private static final int ID_COL = 0;
	private static final int NAME_COL = 1;
	private static final int TYPE_COL = 2;
	private static final int PRICE_COL = 3;
	private static final int VERSION_COL = 4;
	private static final int RELEASEDATE_COL = 5;
	private static final int AGERATING_COL = 6;
	private static final int PUBLISHER_COL = 7;
	static final int OBJECT_COL = -1;
	
	private String[] columnNames = { "номер игры", "название", "вид игры", 
			"цена", "издание", "дата выхода", "возрастная категория", "издатель"};
	private List<Game> games;
	
	public GameTableModel(List<Game> theGames) {
		games = theGames;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public int getRowCount() {
		return games.size();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		Game tempGame = games.get(row);
		
		switch(col) {
			case OBJECT_COL:
				return tempGame;
			case ID_COL:
				return tempGame.getId();
			case NAME_COL:  
				return tempGame.getName();
			case TYPE_COL:  
				return tempGame.getType();
			case PRICE_COL: 
				return tempGame.getPrice();
			case VERSION_COL:
				return tempGame.getVersion();
			case RELEASEDATE_COL:
				return tempGame.getReleaseDate();
			case AGERATING_COL:
				return tempGame.getAgeRating();
			case PUBLISHER_COL:
				return tempGame.getPublisher();
			default:
				return tempGame.getName();
		}
	}
	
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}