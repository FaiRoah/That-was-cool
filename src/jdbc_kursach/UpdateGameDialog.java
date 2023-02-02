package jdbc_kursach;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import gamesearch.core.Game;
import gamesearch.core.User;
import gamesearch.dao.GameDAO;
import java.sql.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateGameDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField nameTextField;
	private JTextField typeTextField;
	private JTextField priceTextField;
	private JTextField versionTextField;
	private JTextField releaseDateTextField;
	private JTextField ageRatingTextField;
	private JTextField publisherTextField;

	private GameDAO gameDAO;
	private User user;
	private TestApp testApp;

	private Game previousGame = null;
	private boolean updateMode = false;
	private JTextField updateNameTextField;
	private JTextField updatePriceTextField;
	private JTextField updateTypeTextField;
	private JTextField updateVersionTextField;
	private JTextField updateReleaseDateTextField;
	private JTextField updateAgeRatingTextField;
	private JTextField updatePublisherTextField;

	public UpdateGameDialog(TestApp theTestApp, GameDAO theGameDAO, Game thePreviousGame, boolean theUpdateMode) {
		this();
		gameDAO = theGameDAO;
		testApp = theTestApp;

		previousGame = thePreviousGame;

		updateMode = theUpdateMode;

		if (updateMode) {
			setTitle("Обновить игру");

			populateGui(previousGame);
		}
	}

	private void populateGui(Game theGame) {
		updateNameTextField.setText(theGame.getName());
		updateTypeTextField.setText(theGame.getType());
		updatePriceTextField.setText(String.valueOf(theGame.getPrice()));
		updateVersionTextField.setText(theGame.getVersion());
		updateReleaseDateTextField.setText(String.valueOf(theGame.getReleaseDate()));
		updateAgeRatingTextField.setText(theGame.getAgeRating());
		updatePublisherTextField.setText(theGame.getPublisher());

	}

	protected void saveGame() throws ParseException {

		String name = updateNameTextField.getText();
		String type = updateTypeTextField.getText();

		String priceStr = updatePriceTextField.getText();
		Integer price = Integer.parseInt(priceStr);

		String version = updateVersionTextField.getText();

		String releaseDateStr = updateReleaseDateTextField.getText();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ROOT);
		Date releaseDate = new java.sql.Date(formatter.parse(releaseDateStr).getTime()); // TODO:wrong mm being parsed

		String ageRating = updateAgeRatingTextField.getText();
		String publisher = updatePublisherTextField.getText();

		Game tempGame = null;

		if (updateMode) {
			tempGame = previousGame;

			tempGame.setName(name);
			tempGame.setType(type);
			tempGame.setPrice(price);
			tempGame.setVersion(version);
			tempGame.setReleaseDate(releaseDate);
			tempGame.setAgeRating(ageRating);
			tempGame.setPublisher(publisher);

		} else {
			tempGame = new Game(name, type, price, version, releaseDate, ageRating, publisher);
		}

		try {
			if (updateMode) {
				System.out.println();
				gameDAO.updateGame(tempGame, User.getLoggedInUserId());
				updateMode = false;
			} else {
				gameDAO.addGame(tempGame);
			}

			setVisible(false);
			dispose();

			testApp.refreshGamesView();

			JOptionPane.showMessageDialog(testApp, "Игра успешно обновлена.", "Игра обновлена",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UpdateGameDialog dialog = new UpdateGameDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public UpdateGameDialog() {
		setTitle("Обновить игру");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));
		{
			JLabel updateGameNameLbl = new JLabel("Название игры");
			updateGameNameLbl.setHorizontalAlignment(SwingConstants.LEFT);
			contentPanel.add(updateGameNameLbl, "2, 2, right, default");
		}
		{
			updateNameTextField = new JTextField();
			contentPanel.add(updateNameTextField, "4, 2, fill, default");
			updateNameTextField.setColumns(10);
		}
		{
			JLabel updateTypeLbl = new JLabel("Вид игры");
			contentPanel.add(updateTypeLbl, "2, 4, right, default");
		}
		{
			updateTypeTextField = new JTextField();
			contentPanel.add(updateTypeTextField, "4, 4, fill, default");
			updateTypeTextField.setColumns(10);
		}
		{
			JLabel updatePriceLbl = new JLabel("Цена");
			contentPanel.add(updatePriceLbl, "2, 6, right, default");
		}
		{
			updatePriceTextField = new JTextField();
			updatePriceTextField.setColumns(10);
			contentPanel.add(updatePriceTextField, "4, 6, fill, default");
		}
		{
			JLabel updateVersionlbl = new JLabel("Издание");
			contentPanel.add(updateVersionlbl, "2, 8, right, default");
		}
		{
			updateVersionTextField = new JTextField();
			contentPanel.add(updateVersionTextField, "4, 8, fill, default");
			updateVersionTextField.setColumns(10);
		}
		{
			JLabel updateReleaseDateLbl = new JLabel("Дата выхода");
			contentPanel.add(updateReleaseDateLbl, "2, 10, right, default");
		}
		{
			updateReleaseDateTextField = new JTextField();
			contentPanel.add(updateReleaseDateTextField, "4, 10, fill, default");
			updateReleaseDateTextField.setColumns(10);
		}
		{
			JLabel updateAgeRatingLbl = new JLabel("Возрастная категория");
			contentPanel.add(updateAgeRatingLbl, "2, 12, right, default");
		}
		{
			updateAgeRatingTextField = new JTextField();
			contentPanel.add(updateAgeRatingTextField, "4, 12, fill, default");
			updateAgeRatingTextField.setColumns(10);
		}
		{
			JLabel updatePublisherLbl = new JLabel("Издатель");
			contentPanel.add(updatePublisherLbl, "2, 14, right, default");
		}
		{
			updatePublisherTextField = new JTextField();
			contentPanel.add(updatePublisherTextField, "4, 14, fill, default");
			updatePublisherTextField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Сохранить");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							saveGame();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Отменить");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
