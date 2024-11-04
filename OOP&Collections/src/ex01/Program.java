package ex01;

public class Program {

	public static void main(String[] args) {
		User user1 = new User("Mike", 100);
		User user2 = new User("Jhon", 200);
		User user3 = new User("Bob");

		System.out.println("Users:");
		System.out.println(user1.getIdentifier() + " " + user1.getName() + " " + user1.getBalance());
		System.out.println(user2.getIdentifier() + " " + user2.getName() + " " + user2.getBalance());
		System.out.println(user3.getIdentifier() + " " + user3.getName() + " " + user3.getBalance());
	}
}