package jdbc_kursach;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jasypt.util.password.StrongPasswordEncryptor;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import gamesearch.core.User;
import gamesearch.dao.GameDAO;
import gamesearch.dao.UserDAO;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class UserLoginDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox userLoginComboBox;
	
	private UserDAO userDAO;
	private User user;
	private JPasswordField userPswdTextField;
	public void setUserDAO(UserDAO theUserDAO) {
		userDAO = theUserDAO;
	}
	
	public void populateUsers(List<User> users) {
		List<String> userNames = new ArrayList<>();
		
		for (int i=0; i<users.size(); i++) {
			userNames.add(users.get(i).getLastName() + " " + users.get(i).getFirstName());
		}
		userLoginComboBox.setModel(new DefaultComboBoxModel(userNames.toArray()));
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UserLoginDialog dialog = new UserLoginDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @throws Exception 
	 */
	public UserLoginDialog() throws Exception {
		try {
			setUserDAO(userDAO = new UserDAO());
		} catch (Exception e) {
			JOptionPane.showInputDialog(this, "Error: " + e, JOptionPane.ERROR_MESSAGE);
		}
		setTitle("Вход");
		setBounds(100, 100, 450, 150);
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
				FormSpecs.DEFAULT_ROWSPEC,}));
		{
			JLabel UserLoginLbl = new JLabel("Пользователь");
			contentPanel.add(UserLoginLbl, "2, 2, right, default");
		}
		{
			userLoginComboBox = new JComboBox();
			contentPanel.add(userLoginComboBox, "4, 2, fill, default");
			populateUsers(userDAO.getAllUsers());
		}
		{
			JLabel userPasswordLbl = new JLabel("Пароль");
			contentPanel.add(userPasswordLbl, "2, 4, right, default");
		}
		{
			userPswdTextField = new JPasswordField();
			contentPanel.add(userPswdTextField, "4, 4, fill, default");
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						User selectedUser;
						try {
							selectedUser = userDAO.convertNameToUser(userLoginComboBox.getSelectedItem().toString());
							
							selectedUser.setLoggedInUserId(selectedUser.getId());
							String plainTextPswd = new String(userPswdTextField.getPassword());
							
							boolean isValidPswd = userDAO.authenticate(selectedUser, plainTextPswd);
							
							if (isValidPswd) {
								setVisible(false);
								dispose();
								TestApp frame = new TestApp(selectedUser);
								frame.setVisible(true);
							}
							else {
								JOptionPane.showInputDialog(UserLoginDialog.this, "Invalid password", "Invalid Password", JOptionPane.ERROR_MESSAGE);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
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

}
