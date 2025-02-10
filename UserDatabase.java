import java.util.ArrayList;

public class UserDatabase {
	private static ArrayList<User> users = new ArrayList<User>();
	private static int numberOfUsers = 0;
	
	public UserDatabase() {
		//just here to construct the object
	}
	
	public static ArrayList<User> getUsers() {
		return users;
	}
	public static int getNumberOfUsers() {
		return numberOfUsers;
	}
	public static boolean addUser(String username) {
		if (username != null) {
			users.add(new User(username));
			numberOfUsers++;
			return true;
		} else {
			users.add(new User());
			numberOfUsers++;
			return false;
		}
	}
	public static boolean removeUser(String username) {
		for (User user : users) {
			if (user.getNAME().equals(username)) {
				users.remove(users.indexOf(user));
				numberOfUsers--;
				return true;
			}
		}
		return false;
	}
	public static boolean removeUser(int i) {
		if (i < numberOfUsers && i >= 0) {
			users.remove(i);
			numberOfUsers--;
			return true;
		}
		return false;
	}
	public static String toString(String placeholder) {
		String message = "Users in Database:";
		String newLine = System.getProperty("line.separator");
		for (int i = 0; i < numberOfUsers; i++) {
			message += newLine + " " + users.get(i).getUserID() + " " + users.get(i).getNAME();
		}
		return message;
	}
}
