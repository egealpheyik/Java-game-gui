import java.util.ArrayList;
import junit.framework.TestCase;

public class TestChild extends TestCase {

    private Child child;
    private Parent parent;
    private Exercise exercise1;

    protected void setUp() {
        parent = new Parent("parentusername", "parentpassword");
        child = new Child("testusername", "testpassword", parent);
        exercise1 = new Exercise(2, 5, 7);
    }

    public void testParentAndChild() {
        assertEquals("parentusername", parent.getUsername());
        assertEquals("parentpassword", parent.getPassword());
        assertEquals("testusername", child.getUsername());
        assertEquals("testpassword", child.getPassword());
    }

    public void testAddingChildToParent() {
        parent.addChild(child);
        ArrayList<Child> childList = parent.getChildren();
        assertEquals(child, childList.get(0));
    }

    public void testExercises() {
        exercise1.setTime(10);
        exercise1.setTrueNumber(5);
        assertEquals(2, exercise1.getStartNumber());
        assertEquals(5, exercise1.getEndNumber());
        assertEquals(7, exercise1.getQuestionNumber());
        assertEquals(10, exercise1.getTime());
        assertEquals(5, exercise1.getTrueNumber());
    }

    public void testAddExercise() {
        child.addExercise(exercise1);
        ArrayList<Exercise> exercises = child.getExercises();
        assertEquals(exercise1, exercises.get(0));
    }
}

