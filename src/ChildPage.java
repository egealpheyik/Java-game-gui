import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChildPage {
    private Child child;

    public ChildPage(Child child) {
    	FileIO fileIO = new FileIO();
    	fileIO.loadExercisesForChild(child);
        this.child = child;
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Child Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);

        JLabel usernameLabel = new JLabel("Welcome, " + child.getUsername());
        usernameLabel.setBounds(10, 10, 200, 20);
        contentPanel.add(usernameLabel);
        
        
        if (child.hasPendingExercise()) {
            JLabel exerciseLabel = new JLabel("Bekleyen egzersiz var");
            exerciseLabel.setBounds(100, 50, 200, 20);
            contentPanel.add(exerciseLabel);
            JButton startButton = new JButton("Başlat");
            startButton.setBounds(130, 80, 80, 30);
            contentPanel.add(startButton);
            
            startButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	frame.dispose();
                	new GamePage(child);
                	
                }
            });
            
        }
        else {
            JLabel noExerciseLabel = new JLabel("Bekleyen egzersiz yok");
            noExerciseLabel.setBounds(100, 50, 200, 20);
            contentPanel.add(noExerciseLabel);
        }

        frame.getContentPane().add(contentPanel);

        frame.setSize(300, 200);
        frame.setVisible(true);
    }


}
