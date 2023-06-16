import java.util.ArrayList;

public class Parent extends User {
    public ArrayList<Child> children;

    public Parent(String username, String password) {
        super(username, password);
        children = new ArrayList<Child>();
    }

    public ArrayList<Child> getChildren() {
        return children;
    }
    
    public ArrayList<String> getChildrenNames(){
    	ArrayList<String> names = new ArrayList<String>();
    	for(Child child:children) {
    		names.add(child.username);
    	}
    	return names;
    }

    
    public void setChildren(ArrayList<Child> children) {
		this.children = children;
	}

	public void addChild(Child child) {
    	children.add(child);
    }
    
    public void removeChild(Child child) {
    	children.remove(child);
    }
}
