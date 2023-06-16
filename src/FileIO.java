import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class FileIO {
    FileWriter writer;

    public FileIO() {
    	
    }
        public void saveUser(String userType, String username, String password) {
            try {
                writer = new FileWriter("users.txt", true);
            } catch (IOException e) {
                System.out.println("File cannot be opened");
                e.printStackTrace();
            }

            try {
                writer.write(userType + "," + username + "," + password + "\n");
                writer.close();
            } catch (IOException e) {
                closeWriter(writer);
                e.printStackTrace();
            }
        }


        public boolean addChildToParent(String parentUsername, String childUsername, String childPassword) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
                StringBuilder sb = new StringBuilder();
                String line;

                boolean parentFound = false;
                boolean childFound = false;

                while ((line = reader.readLine()) != null) {
                    String[] userData = line.split(",");
                    String userType = userData[0];
                    String username = userData[1];

                    if (username.equals(parentUsername) && userType.equals("p")) {
                        parentFound = true;
                        if (!isChildAlreadyAdded(line, childUsername)) {
                            sb.append(line).append(";c,").append(childUsername).append(",").append(childPassword).append("\n");
                        } else {
                            childFound = true;
                            System.out.println("Child is already added to the parent.");
                        }
                    } else {
                        sb.append(line).append("\n");
                    }
                }

                reader.close();

                if (!parentFound) {
                    System.out.println("Parent not found.");
                    return false;
                }

                if (childFound) {
                    return false;
                }

                FileWriter writer = new FileWriter("users.txt");
                writer.write(sb.toString());
                writer.close();

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        private boolean isChildAlreadyAdded(String line, String childUsername) {
            String[] userData = line.split(";");
            for (String data : userData) {
                if (data.startsWith("c,")) {
                    String[] childData = data.split(",");
                    if (childData[1].equals(childUsername)) {
                        return true;
                    }
                }
            }
            return false;
        }


        public boolean searchUser(String username, String password) throws IOException {
            boolean found = false;
            String key;

            try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
                while (!found && (key = reader.readLine()) != null) {
                    String[] userData = key.split(",");
                    String storedUsername = userData[1];
                    String storedPassword = userData[2];

                    if (username.equals(storedUsername) && password.equals(storedPassword)) {
                        found = true;
                        return true;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return false;
        }
        public boolean searchChild(String username, String password) {
            boolean found = false;
            String key;

            try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
                while (!found && (key = reader.readLine()) != null) {
                    String[] userData = key.split(",");
                    String userType = userData[0];
                    String storedUsername = userData[1];
                    String storedPassword = userData[2];

                    if (userType.equals("c") && username.equals(storedUsername) && password.equals(storedPassword)) {
                        found = true;
                        return true;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        
        public boolean searchParent(String username, String password) {
            boolean found = false;
            String key;

            try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
               
            	while (!found && (key = reader.readLine()) != null) {
                    String[] userData = key.split(",");
                    String userType = userData[0];
                    String storedUsername = userData[1];
                    String storedPassword = userData[2];
                    if(storedPassword.contains(";")) { // for the parents that have child
                    	int index = storedPassword.indexOf(';');
                    	storedPassword = storedPassword.substring(0, index);
                    }
                    System.out.println(storedPassword);
                    if (userType.equals("p") && username.equals(storedUsername) && password.equals(storedPassword)) {
                        found = true;
                        return true;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }
        public ArrayList<Child> getChildrenOfParent(String parentUsername) {
            ArrayList<Child> children = new ArrayList<Child>();

            try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] userData = line.split(",");
                    String userType = userData[0];
                    String username = userData[1];

                    if (userType.equals("p") && username.equals(parentUsername)) {
                        int index = line.indexOf(";c,");
                        if (index != -1) {
                            String childrenData = line.substring(index + 3);
                            String[] childrenArray = childrenData.split(";c,");
                            for (String child : childrenArray) {
                                String[] childData = child.split(",");
                                String childUsername = childData[0];
                                String childPassword = childData[1];
                                Child childObj = new Child(childUsername, childPassword, null);
                                children.add(childObj);
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return children;
        }


        public void closeWriter(FileWriter writer) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void loadExercisesForChild(Child child) {
            try {
            	String fileName = child.getUsername() + "_" + child.getPassword();
                BufferedReader reader = new BufferedReader(new FileReader("child/" + fileName));
                ArrayList<Exercise> exercises = new ArrayList<>();

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] exerciseData = line.split(",");
                    if (exerciseData.length == 6) {
                    	int from = Integer.parseInt(exerciseData[2]);
                        int to = Integer.parseInt(exerciseData[3]);
                        int numQuestions = Integer.parseInt(exerciseData[4]);
                        int time = Integer.parseInt(exerciseData[1]);
                        //int time = exerciseData[1];
                        int trueNumber = Integer.parseInt(exerciseData[5]);
                        Exercise exercise = new Exercise(from, to, numQuestions);
                        exercise.setTime(time);
                        exercise.setTrueNumber(trueNumber);
                    	if(exerciseData[0].equals("n")) {
                    		//child.addExercise(exercise);
                            exercises.add(exercise);
                    	}
                    	else if(exerciseData[0] == "f") {
                    		
                    		child.addFinishedExercise(exercise);
                    	}
                        
                    }
                }

                reader.close();

                child.setExercises(exercises);
                
            } catch (IOException e) {
                System.out.println("Egzersizler yüklenirken bir hata oluştu: " + e.getMessage());
            }
        }
        public void finishExercise(String username, String password, Exercise exercise, int Seconds) {
        	//System.out.println(exercise.getTrueNumber());
            String fileName = username + "_" + password;
            String filePath = "child/" + fileName;

            try {
                //File file = new File(filePath);
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                StringBuilder content = new StringBuilder();
                String line;
                String notFinishedExercise = "n," + exercise.toStringFile();
                String finishedExercise = "f," + exercise.toStringUpdatedFile();
                while ((line = reader.readLine()) != null) {
                	if (line.equals(notFinishedExercise)) {
                		System.out.println(line);
                		line = finishedExercise;
                		System.out.println(line);
                	    
                	}
                	content.append(line).append("\n");

            
                    
                    
                }

                reader.close();

                FileWriter writer = new FileWriter(filePath);
                writer.write(content.toString());
                writer.close();
            } catch (IOException e) {
                System.out.println("Dosya işlenirken bir hata oluştu: " + e.getMessage());
            }
        }
        


    
}
