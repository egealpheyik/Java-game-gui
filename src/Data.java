import java.util.HashMap;

public class Data {
	
	HashMap<String, User> data = new HashMap<String, User>();
	
	
	public void addUser(String username) {
		User newUser = new User(username, "123");
		data.put(username, newUser);
	}

}
