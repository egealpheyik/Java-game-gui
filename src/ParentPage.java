import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;


public class ParentPage extends JFrame {
    private static final long serialVersionUID = 1L;
	//private Parent parent;
    private JComboBox<Child> childrenComboBox;
    //private JTextArea exerciseTextArea;
    private JTextField childNameField;
    private JTextField childExerciseField;
    private Child selectedChild;
    

    public ParentPage(Parent parent) {
        //this.parent = parent;
        FileIO fileIO = new FileIO();
        ArrayList<Child> children = fileIO.getChildrenOfParent(parent.getUsername());
        parent.setChildren(children);
        if(children.isEmpty()) selectedChild = null;
        else {
        	selectedChild = children.get(0);
        }

        setTitle("Parent Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Children section
        JPanel childrenPanel = new JPanel();
        childrenPanel.setBorder(BorderFactory.createTitledBorder("Children"));
        childrenPanel.setLayout(new BorderLayout());

        childrenComboBox = new JComboBox<>(parent.getChildren().toArray(new Child[0]));
        childrenComboBox.setRenderer(new ChildComboBoxRenderer());
        childrenComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedChild = (Child) childrenComboBox.getSelectedItem();
                if (selectedChild != null) {
                    
                }
            }
        });

        childrenPanel.add(childrenComboBox, BorderLayout.NORTH);

        mainPanel.add(childrenPanel);

        JPanel addExercisePanel = new JPanel();
        addExercisePanel.setBorder(BorderFactory.createTitledBorder("Add Exercise"));
        addExercisePanel.setLayout(new BorderLayout());

        JTextField from = new JTextField();
        JTextField to = new JTextField();
        JTextField numQuestionsField = new JTextField();
        JLabel addExerciseNotification = new JLabel();

        JPanel exerciseSettingPanel = new JPanel();
        exerciseSettingPanel.setLayout(new GridLayout(4, 2));
        exerciseSettingPanel.add(new JLabel("from:"));
        exerciseSettingPanel.add(from);
        exerciseSettingPanel.add(new JLabel("to:"));
        exerciseSettingPanel.add(to);
        exerciseSettingPanel.add(new JLabel("Number of Questions:"));
        exerciseSettingPanel.add(numQuestionsField);
        exerciseSettingPanel.add(addExerciseNotification);

        JButton addExerciseButton = new JButton("Add");
        addExerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numQuestionsText = numQuestionsField.getText();
                String fromText = from.getText();
                String toText = to.getText();
                if(selectedChild == null) addExerciseNotification.setText("choose a child");
                else {
                	if (!numQuestionsText.isEmpty() && !fromText.isEmpty() && !toText.isEmpty()) {
                        int numQuestions = Integer.parseInt(numQuestionsText);
                        int from = Integer.parseInt(fromText);
                        int to = Integer.parseInt(toText);
                        
                        Exercise exercise = new Exercise(from, to, numQuestions);
                        exercise.setTime(0);
                        exercise.setTrueNumber(0);
                        selectedChild.addExercise(exercise);
                        
                        String exerciseInfo = exercise.toStringFile();
                        exerciseInfo = "n," + exerciseInfo;
                        
                       
                        try {
                        	
                        	String fileName = selectedChild.getUsername() + "_" + selectedChild.getPassword();
                            FileWriter fileWriter = new FileWriter("child/" + fileName, true);
                            //fileWriter.write(selectedChild.get + "," + password + "\n");
                            fileWriter.write(exerciseInfo);
                            fileWriter.write("\n");
                            fileWriter.close();
                            System.out.println("Dosya oluşturuldu: " + fileName);
                        } catch (IOException e1) {
                            System.out.println("Dosya oluşturulurken bir hata oluştu: " + e1.getMessage());
                        }
                        
                        addExerciseNotification.setText("Exercise added");
                    }
                    else {
                        addExerciseNotification.setText("Provide valid input");
                    }
                }
                
            } 
        });

        addExercisePanel.add(exerciseSettingPanel, BorderLayout.NORTH);
        addExercisePanel.add(addExerciseButton, BorderLayout.CENTER);

        mainPanel.add(addExercisePanel);

        // Child addition section
        JPanel addChildPanel = new JPanel();
        addChildPanel.setBorder(BorderFactory.createTitledBorder("Add Child"));
        addChildPanel.setLayout(new BorderLayout());

        JLabel notification = new JLabel();

        childNameField = new JTextField();
        childExerciseField = new JTextField();
        JButton addChildButton = new JButton("Add Child");
        addChildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String childUsername = childNameField.getText();
                String childPassword = childExerciseField.getText();
                if (!childUsername.isEmpty() && !childPassword.isEmpty()) {
                    FileIO fileIO = new FileIO();
                    boolean found = fileIO.searchChild(childUsername, childPassword);
                    System.out.println(found);
					if (found) {
						Boolean isAdded;
						isAdded = fileIO.addChildToParent(parent.getUsername(), childUsername, childPassword);
					    if(isAdded) {
					    	Child newChild = new Child(childUsername, childPassword, parent);
						    parent.addChild(newChild);
						    childrenComboBox.addItem(newChild);
						    notification.setText("Child added");
					    }
					    else {
					    	notification.setText("child " + childUsername + " has already added.");
					    }
						
					} else {
					    
					    notification.setText("There is not a child with given infos. Try again.");
					}
                    childNameField.setText("");
                    childExerciseField.setText("");
                } else {
                    notification.setText("Provide valid username and password");
                }
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Child's Username:"));
        inputPanel.add(childNameField);
        inputPanel.add(new JLabel("Child's Password:"));
        inputPanel.add(childExerciseField);

        addChildPanel.add(inputPanel, BorderLayout.NORTH);
        addChildPanel.add(notification, BorderLayout.CENTER);
        addChildPanel.add(addChildButton, BorderLayout.SOUTH);

        mainPanel.add(addChildPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton homepageButton = new JButton("Return to Login Page");
        homepageButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				@SuppressWarnings("unused")
				LoginPage loginPage = new LoginPage();
				
			}
		});
        buttonPanel.add(homepageButton);
        
        JButton reportButton = new JButton("Rapor Oluştur");
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Report report = new Report(selectedChild);
                String reportString = report.createReport();
                notification.setText(reportString);
                
            }
        });
        buttonPanel.add(reportButton);
        
        mainPanel.add(buttonPanel);

        add(mainPanel);

        setVisible(true);
    }



    private class ChildComboBoxRenderer extends DefaultListCellRenderer {
        private static final long serialVersionUID = 1L;

		@Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Child) {
                value = ((Child) value).getUsername(); // Get the name of the child
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }
}
