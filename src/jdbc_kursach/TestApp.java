package jdbc_kursach;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gamesearch.dao.AuditHistoryDAO;
import gamesearch.dao.GameDAO;

import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.nio.channels.SelectableChannel;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.List;

import gamesearch.core.AuditHistory;
import gamesearch.core.Game;
import gamesearch.core.User;
import gamesearch.dao.GameDAO;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.SwingConstants;

public class TestApp extends JFrame {
	static final int OBJECT_COL = -1;

	private JPanel contentPane;

	private GameDAO gameDAO;
	private AuditHistoryDAO auditHistoryDAO;
	private JTable table;
	private JTextField priceFilterTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

	}

	/**
	 * Create the frame.
	 */
	public TestApp(User loggedInUser) {
		try {
			gameDAO = new GameDAO();
			auditHistoryDAO = new AuditHistoryDAO();
		} catch (Exception e) {
			JOptionPane.showInputDialog(this, "Error: " + e, JOptionPane.ERROR_MESSAGE);
		}

		setTitle("БД Магазин видеоигр");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 755, 421);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);

		JButton btnAddGame = new JButton("Добавить игру");
		btnAddGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddGameDialog dialog = new AddGameDialog(TestApp.this, gameDAO);

				dialog.setVisible(true);
			}
		});
		panel.add(btnAddGame);

		JButton btnDeleteGame = new JButton("Удалить игру");
		if (loggedInUser.getIs_admin()) {
			btnDeleteGame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					try {
						int[] rows = table.getSelectedRows();

						if (rows.length < 0) {
							JOptionPane.showMessageDialog(TestApp.this, "Вы должны выбрать игру, прежде чем удалить",
									"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}

						int response = JOptionPane.showConfirmDialog(TestApp.this, "Удалить игру?", "Подтвердить",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

						if (response != JOptionPane.YES_OPTION) {
							return;
						}
						
						for (int i = 0; i < rows.length; i++) {
							Game tempGame = (Game) table.getValueAt(rows[i], OBJECT_COL);
	
							gameDAO.deleteGame(tempGame.getId());
						}
						refreshGamesView();

						JOptionPane.showMessageDialog(TestApp.this, "Игра успешно удалена.", "Игра удалена",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception exc) {
						JOptionPane.showMessageDialog(TestApp.this, "Error: " + exc.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);

					}
				}
			});
		} else {
			btnDeleteGame.setEnabled(false);
		}
		panel.add(btnDeleteGame);

		JButton btnUpdateGame = new JButton("Обновить игру");
		
			btnUpdateGame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int row = table.getSelectedRow();

					if (row < 0) {
						JOptionPane.showMessageDialog(TestApp.this, "Вы должны выбрать игру, прежде чем обновить",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					Game tempGame = (Game) table.getValueAt(row, OBJECT_COL);

					UpdateGameDialog dialog = new UpdateGameDialog(TestApp.this, gameDAO, tempGame, true);

					dialog.setVisible(true);
				}
			});
		
		panel.add(btnUpdateGame);

		JButton historyBtn = new JButton("История");
		historyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row < 0) {
					JOptionPane.showMessageDialog(TestApp.this, "Вы должны выбрать игру, прежде чем посмотреть историю",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				Game tempGame = (Game) table.getValueAt(row, OBJECT_COL);
				try {
					int gameId = tempGame.getId();
					List<AuditHistory> auditHistoryList = auditHistoryDAO.getAuditHistory(gameId);

					AuditHistoryDialog dialog = new AuditHistoryDialog();
					dialog.populate(tempGame, auditHistoryList);

					dialog.setVisible(true);

				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		panel.add(historyBtn);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		
				JButton btnShowAllGames = new JButton("Показать все игры");
				btnShowAllGames.setHorizontalAlignment(SwingConstants.LEFT);
				btnShowAllGames.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							List<Game> games = null;
							games = gameDAO.getAllGames();
							GameTableModel model = new GameTableModel(games);

							table.setModel(model);
						} catch (Exception exc) {
							exc.printStackTrace();
						}
					}
				});
				panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
				panel_1.add(btnShowAllGames);
				
				JButton filterPriceBtn = new JButton("Отфильтровать по цене");
				filterPriceBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						List<Game> filteredGames = null;
						try {
							
							filteredGames = gameDAO.filterByPrice(Integer.valueOf(priceFilterTextField.getText()));
						} catch (Exception e2) {
							JOptionPane.showInputDialog(TestApp.this, "Введите фильтр ", "Error", JOptionPane.ERROR_MESSAGE);
						}
						GameTableModel model = new GameTableModel(filteredGames);
						
						table.setModel(model);
					}
				});
				panel_1.add(filterPriceBtn);
				
				priceFilterTextField = new JTextField();
				panel_1.add(priceFilterTextField);
				priceFilterTextField.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);
	}

	public void refreshGamesView() {
		// TODO Auto-generated method stub

		try {
			List<Game> games = gameDAO.getAllGames();

			GameTableModel model = new GameTableModel(games);

			table.setModel(model);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: ", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
