import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class LoginPage implements ActionListener {
    JFrame frame = new JFrame(); // creates a frame
    JPanel panel = new JPanel();
    JButton loginButton;
    JButton signinButton;
    JTextField usernameTextField;
    JTextField passwordField;
    JLabel passwordLabel;
    JLabel usernameLabel;
    JLabel resultLabel;
    JCheckBox parentCheckBox;
    
    User user;
    
    LoginPage() {
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);

        usernameLabel = new JLabel("username");
        usernameLabel.setBounds(10, 20, 80, 25);
        panel.add(usernameLabel);

        usernameTextField = new JTextField();
        usernameTextField.setBounds(100, 20, 165, 25);
        panel.add(usernameTextField);

        passwordLabel = new JLabel("password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JTextField();
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(170, 80, 80, 25);
        panel.add(loginButton);
        loginButton.addActionListener(this);

        signinButton = new JButton("Sign In");
        signinButton.setBounds(170, 110, 80, 25);
        panel.add(signinButton);
        signinButton.addActionListener(this);
        
        resultLabel = new JLabel();
        resultLabel.setBounds(15, 120, 500, 30);
        panel.add(resultLabel);

        parentCheckBox = new JCheckBox("I am a parent"); 
        parentCheckBox.setBounds(10, 80, 120, 25);
        panel.add(parentCheckBox);
        
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        if (e.getSource() == loginButton) {
            if (username.isEmpty() || password.isEmpty()) {
                resultLabel.setText("Enter a valid username and password!");
            } else {
                boolean isChild = !parentCheckBox.isSelected();
                try {
                    User user = searchUser(username, password, isChild);
                    if (user instanceof Child) {
                        Child child = new Child(username, password, null);
                        frame.dispose();
                        ChildPage childPage = new ChildPage(child);
                        childPage.createAndShowGUI();
                    } else if (user instanceof Parent) {
                        Parent parent = new Parent(username, password);
                        frame.dispose();
                        @SuppressWarnings("unused")
						ParentPage parentPage = new ParentPage(parent);
                    } else {
                        resultLabel.setText("Wrong username or password!");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        if (e.getSource() == signinButton) {
            frame.dispose();
            @SuppressWarnings("unused")
			SignInPage signInPage = new SignInPage();
        }
    }

    public User searchUser(String username, String password, boolean isChild) throws IOException {
        FileIO fileIO = new FileIO();
        boolean found;
        if (isChild) {
            found = fileIO.searchChild(username, password);
            if (found) {
                return new Child(username, password, null);
            }
        } else {
            found = fileIO.searchParent(username, password);
            if (found) {
                return new Parent(username, password);
            }
        }
        return null;
    }


}
