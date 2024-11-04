package ex02;

public class Program {

	public static void main(String[] args) {
		UsersList myList = new UsersArrayList();

		for (int i = 1; i < 12; ++i) {
			myList.addUser(new User("Mike" + i, i));
		}

		
		System.out.println("NumberOfUsers = " + myList.getNumberOfUsers());
		System.out.println("User 10 = " + myList.getUserById(10).getName() + " ID = " + myList.getUserById(10).getIdentifier());

		try {
			myList.getUserById(-10);
		} catch (UserNotFoundException e) {
			System.out.println("\nException by ID");
		}

		try {
			myList.getUserByIndex(-10);
		} catch (UserNotFoundException e) {
			System.out.println("Exception by Index");
		}


		UsersArrayList myArrayList = new UsersArrayList();

		for (int i = 1; i < 12; ++i) {
			myArrayList.addUser(new User("Mike" + i, i));
		}

		System.out.println("\nCapacity = " + myArrayList.getCapacity());

	}
}