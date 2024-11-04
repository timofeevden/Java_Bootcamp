package ex00;	//	javac -d ./ ./*.java && java ex00/Program

public class Program {

	public static void main(String[] args) {
		User user1 = new User(1, "Mike", 100);
		User user2 = new User(2, "Jhon", 200);
		User user3 = new User(3, "Bob");

		System.out.println("Users before transactions:");
		System.out.println(user1.getIdentifier() + " " + user1.getName() + " " + user1.getBalance());
		System.out.println(user2.getIdentifier() + " " + user2.getName() + " " + user2.getBalance());
		System.out.println(user3.getIdentifier() + " " + user3.getName() + " " + user3.getBalance());

		Transaction transaction1 = new Transaction(user1, user2, Transaction.TransferCategory.DEBIT, 50);
		
		System.out.println("\nFirst transaction:");
		System.out.println(transaction1.getIdentifier() + " " + transaction1.getRecipient().getName() + " " + transaction1.getSender().getName() 
			+ " " + transaction1.getTransferCategory() + " " + transaction1.getTransferAmount());

		System.out.println("\nUsers after 1 transactions:");
		System.out.println(user1.getIdentifier() + " " + user1.getName() + " " + user1.getBalance());
		System.out.println(user2.getIdentifier() + " " + user2.getName() + " " + user2.getBalance());
		System.out.println(user3.getIdentifier() + " " + user3.getName() + " " + user3.getBalance());

		Transaction transaction2 = new Transaction(user2, user3, Transaction.TransferCategory.CREDIT, -5);

		System.out.println("\nSecond transaction:");
		System.out.println(transaction2.getIdentifier() + " " + transaction2.getRecipient().getName() + " " + transaction2.getSender().getName() 
			+ " " + transaction2.getTransferCategory() + " " + transaction2.getTransferAmount());

		System.out.println("\nUsers after 2 transactions:");
		System.out.println(user1.getIdentifier() + " " + user1.getName() + " " + user1.getBalance());
		System.out.println(user2.getIdentifier() + " " + user2.getName() + " " + user2.getBalance());
		System.out.println(user3.getIdentifier() + " " + user3.getName() + " " + user3.getBalance());

		Transaction transaction3 = new Transaction(user3, user1, Transaction.TransferCategory.CREDIT, 100000);
		Transaction transaction4 = new Transaction(user2, user3, Transaction.TransferCategory.CREDIT, 5);

		System.out.println("\nThird transaction:");
		System.out.println(transaction3.getIdentifier() + " " + transaction3.getRecipient().getName() + " " + transaction3.getSender().getName() 
			+ " " + transaction3.getTransferCategory() + " " + transaction3.getTransferAmount());

		System.out.println("\nUsers after transactions:");
		System.out.println(user1.getIdentifier() + " " + user1.getName() + " " + user1.getBalance());
		System.out.println(user2.getIdentifier() + " " + user2.getName() + " " + user2.getBalance());
		System.out.println(user3.getIdentifier() + " " + user3.getName() + " " + user3.getBalance());
	}
}