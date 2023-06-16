import java.util.ArrayList;

public class Child extends User {
    private Parent parent;
    private ArrayList<Exercise> exercises;
    private ArrayList<Exercise> finishedExercises;

    

	public Child(String username, String password, Parent parent) {
        super(username, password);
        this.parent = parent;
        exercises = new ArrayList<Exercise>();
        finishedExercises = new ArrayList<Exercise>();
    }

    public Parent getParent() {
        return parent;
    }
    
    public void setParent(Parent parent) {
    	this.parent = parent;
    }
    
	public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }
    
    public void addExercise(Exercise exercise) {
    	this.exercises.add(exercise);
    }
    
    public boolean hasPendingExercise() {
    	if(exercises.isEmpty()) return false;
    	return true;
    }
    public void removeExercise(Exercise exercise) {
    	exercises.remove(exercise);
    }
    public ArrayList<Exercise> getFinishedExercises() {
		return finishedExercises;
	}

	public void setFinishedExercises(ArrayList<Exercise> finishedExercises) {
		this.finishedExercises = finishedExercises;
	}
	public void addFinishedExercise(Exercise exercise) {
		finishedExercises.add(exercise);
	}
}
