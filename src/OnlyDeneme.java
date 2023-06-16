import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OnlyDeneme extends JFrame {
    private static final long serialVersionUID = 1L;
	private JLabel welcomeLabel;
    private JLabel questionLabel;
    private JTextField answerTextField;
    private JButton submitButton;
    private JLabel resultLabel;

    public OnlyDeneme() {
        setTitle("Çarpım Tablosu Oyunu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 250);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5); 

        welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(welcomeLabel, constraints);
        
        int a = 4; int b = 3;
        
        questionLabel = new JLabel(a +  " * " + b +" = ?");
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.gridy = 1;
        add(questionLabel, constraints);

        answerTextField = new JTextField(10);
        constraints.gridy = 2;
        add(answerTextField, constraints);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitButtonClicked(a);
                
            }
        });
        constraints.gridy = 3;
        add(submitButton, constraints);

        resultLabel = new JLabel();
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.gridy = 4;
        add(resultLabel, constraints);

        setVisible(true);
    }

    private void submitButtonClicked(int a) {
        int answer = Integer.parseInt(answerTextField.getText());
        int expectedAnswer = 12; 
        if (answer == expectedAnswer) {
            resultLabel.setText("Right Answer");
            a = 5;
            
        } else {
            resultLabel.setText("Wrong Answer");
        }
        
       questionLabel.setText("hjffj");
        repaint();
        answerTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OnlyDeneme();
            }
        });
    }
}
