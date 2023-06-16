import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SignInPage {
	JFrame frame = new JFrame("Sign In");
	JButton signInButton = new JButton("Sign In");
	JLabel usernameLabel = new JLabel("Username:");
	JTextField usernameField = new JTextField();
	JLabel passwordLabel = new JLabel("Password:");
	JTextField passwordField = new JTextField();
	JLabel resultLabel = new JLabel();
	JButton returnButton = new JButton("Return to homepage");
	JCheckBox parentCheckbox = new JCheckBox("I am a parent");
	
	public SignInPage() {
        
        frame.setSize(300, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setLayout(null);

        
        usernameLabel.setBounds(40, 30, 80, 25);
        panel.add(usernameLabel);

        
        usernameField.setBounds(120, 30, 150, 25);
        panel.add(usernameField);

        
        passwordLabel.setBounds(40, 60, 80, 25);
        panel.add(passwordLabel);

        
        passwordField.setBounds(120, 60, 150, 25);
        panel.add(passwordField);

        signInButton.setBounds(170, 90, 80, 30);
        panel.add(signInButton);
        
        returnButton.setBounds(40, 130, 200, 30);
        panel.add(returnButton);

        resultLabel.setBounds(10, 150, 300, 50);
        panel.add(resultLabel);
        
        parentCheckbox.setBounds(40, 90, 150, 25);
        panel.add(parentCheckbox);

        
        
        returnButton.addActionListener(new ActionListener() {
			
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				LoginPage loginPage = new LoginPage();
				
			}
		});
       

        signInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	String username = usernameField.getText();
                String password = new String(passwordField.getText());
                if (username.isEmpty() || password.isEmpty()) {
                	resultLabel.setText("Enter a valid username and password");
                }
                else {
                	signInButton.setVisible(false);
                	FileIO fileIO = new FileIO();
                    if(parentCheckbox.isSelected()) {
                    	fileIO.saveUser("p", username, password);
                    	//fileIO.saveParent(username, password);
                    }
                    else {
                    	fileIO.saveUser("c", username, password);
                    }
                    resultLabel.setText("User saved");
                }
                
                
            }
        });
        frame.setVisible(true);
    }

}
