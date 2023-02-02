package jdbc_kursach;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import gamesearch.core.Game;
import gamesearch.dao.GameDAO;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.text.ParseException;
//import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
import java.util.Locale;
import java.awt.event.ActionEvent;

public class AddGameDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField nameTextField;
	private JTextField typeTextField;
	private JTextField priceTextField;
	private JTextField versionTextField;
	private JTextField releaseDateTextField;
	private JTextField ageRatingTextField;
	private JTextField publisherTextField;

	
	private GameDAO gameDAO;
	
	private TestApp testApp;
	
	public AddGameDialog(TestApp theTestApp, GameDAO theGameDAO) {
		this();
		gameDAO = theGameDAO;
		testApp = theTestApp;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddGameDialog dialog = new AddGameDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddGameDialog() {
		setTitle("Добавить игру");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		{
			JLabel lblNewLabel = new JLabel("Название игры");
			lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
			contentPanel.add(lblNewLabel, "2, 2, right, default");
		}
		{
			nameTextField = new JTextField();
			contentPanel.add(nameTextField, "4, 2, fill, default");
			nameTextField.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Вид игры");
			contentPanel.add(lblNewLabel_1, "2, 4, right, default");
		}
		{
			typeTextField = new JTextField();
			typeTextField.setColumns(10);
			contentPanel.add(typeTextField, "4, 4, fill, default");
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Цена");
			contentPanel.add(lblNewLabel_2, "2, 6, right, default");
		}
		{
			priceTextField = new JTextField();
			priceTextField.setColumns(10);
			contentPanel.add(priceTextField, "4, 6, fill, default");
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Издание");
			contentPanel.add(lblNewLabel_3, "2, 8, right, default");
		}
		{
			versionTextField = new JTextField();
			versionTextField.setColumns(10);
			contentPanel.add(versionTextField, "4, 8, fill, default");
		}
		{
			JLabel lblNewLabel_4 = new JLabel("Дата выхода");
			contentPanel.add(lblNewLabel_4, "2, 10, right, default");
		}
		{
			releaseDateTextField = new JTextField();
			releaseDateTextField.setColumns(10);
			contentPanel.add(releaseDateTextField, "4, 10, fill, default");
		}
		{
			JLabel lblNewLabel_5 = new JLabel("Возрастная категория");
			contentPanel.add(lblNewLabel_5, "2, 12, right, default");
		}
		{
			ageRatingTextField = new JTextField();
			ageRatingTextField.setColumns(10);
			contentPanel.add(ageRatingTextField, "4, 12, fill, default");
		}
		{
			JLabel lblNewLabel_6 = new JLabel("Издатель");
			contentPanel.add(lblNewLabel_6, "2, 14, right, default");
		}
		{
			publisherTextField = new JTextField();
			publisherTextField.setColumns(10);
			contentPanel.add(publisherTextField, "4, 14, fill, default");
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
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Отмена");
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
	
	private void saveGame() throws ParseException {
		 String name = nameTextField.getText();
		 String type = typeTextField.getText();
		 
		 String priceStr = priceTextField.getText();
		 Integer price = Integer.parseInt(priceStr);
		 
		 String version = versionTextField.getText();
		 
		 String releaseDateStr = releaseDateTextField.getText();
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ROOT);
		 Date releaseDate = new java.sql.Date(formatter.parse(releaseDateStr).getTime());	//TODO:wrong mm being parsed
		 

		 String ageRating = ageRatingTextField.getText();
		 String publisher = publisherTextField.getText();
		 
		 Game tempGame = new Game(name, type, price, version, releaseDate, ageRating, publisher);
		 System.out.println(tempGame.getReleaseDate());
		 try {
			 gameDAO.addGame(tempGame);
			 
			 setVisible(false);
			 dispose();
			 
			 testApp.refreshGamesView();
			 
			 JOptionPane.showMessageDialog(testApp, "Игра успешно добавлена.", "Игра добавлена", JOptionPane.INFORMATION_MESSAGE);
		 }
		 catch (Exception e) {
			  e.printStackTrace();
		 }
	}
	
}
