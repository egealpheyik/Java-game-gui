import java.util.Random;
import javax.swing.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePage extends JFrame {
    private static final long serialVersionUID = 1L;
	private JLabel welcomeLabel;
    private JLabel questionLabel;
    private JTextField answerTextField;
    private JButton submitButton;
    private JButton returnButton;
    private JLabel resultLabel;
    private JLabel timeJLabel;
    private Timer timer;
    Random random = new Random();
    private int a, b, trueNumber, remainingQuestions, num1, num2;
    int seconds = 0;
    Exercise firstExercise;
    public GamePage(Child child) {
        firstExercise = child.getExercises().get(0);
        remainingQuestions = firstExercise.getQuestionNumber();
        trueNumber = 0;
        a = firstExercise.getStartNumber();
        b = firstExercise.getEndNumber();
        randomizeNumbers();
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

        returnButton = new JButton("Return to Homepage");
        returnButton.setVisible(false);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                child.getExercises().remove(0);
                FileIO fileIO = new FileIO();
                //change the status of that exercise
                
                fileIO.finishExercise(child.getUsername(), child.getPassword(), firstExercise, seconds);
                dispose();
                ChildPage childPage = new ChildPage(child);
                childPage.createAndShowGUI();
            }
        });
        constraints.gridy = 5;
        add(returnButton, constraints);

        questionLabel = new JLabel(num1 + " * " + num2 + " = ?");
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
                submitButtonClicked();

            }
        });
        constraints.gridy = 3;
        add(submitButton, constraints);

        resultLabel = new JLabel();
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.gridy = 4;
        add(resultLabel, constraints);

        timeJLabel = new JLabel();
        timeJLabel.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.gridy = 6;
        add(timeJLabel, constraints);
        
        
        timer = new Timer(1000, new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                String timeString = String.format("%2d.%02d", seconds / 60, seconds % 60);
                timeJLabel.setText("Time Elapsed: " + timeString);
            }
        });
        timer.start();

        setVisible(true);
    }

    private void submitButtonClicked() {
        int answer = Integer.parseInt(answerTextField.getText());
        int expectedAnswer = num1 * num2;
        if (answer == expectedAnswer) {
            resultLabel.setText("Right Answer");
            trueNumber++;
        } else {
            resultLabel.setText("Wrong Answer, " + expectedAnswer);
        }
        remainingQuestions--;

        randomizeNumbers();
        questionLabel.setText(num1 + " * " + num2 + " = ?");
        repaint();
        answerTextField.setText("");
        if (remainingQuestions < 1) {
            endExercise(firstExercise);
        }
    }

    private void endExercise(Exercise exercise) {
        questionLabel.setText("The exercise has ended");
        firstExercise.setTrueNumber(trueNumber);
        firstExercise.setTime(seconds);
        String finishInfo = "You got : " + exercise.getTrueNumber() +  "/" + exercise.getQuestionNumber() + " correct answers in " + exercise.getTime() + " seconds";
        int score = exercise.getTrueNumber()*50/exercise.getTime();
        if(score>10) score = 10;
        //exercise.setScore(score);
        welcomeLabel.setText(finishInfo);
        answerTextField.setVisible(false);
        resultLabel.setText("Your score:" + Integer.toString(score));
        submitButton.setVisible(false);
        returnButton.setVisible(true);
        timer.stop(); // Stop the timer when the exercise ends
        repaint();
    }

    private void randomizeNumbers() {
        num1 = random.nextInt(b - a + 1) + a;
        num2 = random.nextInt(b - a + 1) + a;
    }
}
