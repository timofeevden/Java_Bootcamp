package ex04;

public class Program {

	public static void main(String[] args) {
		TransactionsService service = new TransactionsService();
		System.out.println("Amount users before add: " + service.getNumberOfUsers());

		User user1 = new User("Mike", 100);
		User user2 = new User("Nick", 200);
		User user3 = new User("Bob");

		service.addUser(user1);
		service.addUser(user2);
		service.addUser(user3);

		System.out.println("\nAmount users after add: " + service.getNumberOfUsers());
		System.out.println("user1 balance 100 = " + service.getUserBalance(user1.getIdentifier()));
		System.out.println("user2 balance 200 = " + service.getUserBalance(user2.getIdentifier()));
		System.out.println("user3 balance 0 = " + service.getUserBalance(user3.getIdentifier()));

		service.performTransaction(user1.getIdentifier(), user2.getIdentifier(), 100);
		service.performTransaction(user2.getIdentifier(), user3.getIdentifier(), -5);
		service.performTransaction(user1.getIdentifier(), user2.getIdentifier(), -10);

		System.out.println("\n users after Transactions: ");
		System.out.println("user1 balance 190 = " + service.getUserBalance(user1.getIdentifier()));
		System.out.println("user2 balance 105 = " + service.getUserBalance(user2.getIdentifier()));
		System.out.println("user3 balance 5 = " + service.getUserBalance(user3.getIdentifier()));

		Transaction[] transactionsUser1 = service.getTransactions(user1.getIdentifier());
		Transaction[] transactionsUser2 = service.getTransactions(user2.getIdentifier());
		Transaction[] transactionsUser3 = service.getTransactions(user3.getIdentifier());

		System.out.println("\n user1 Transactions: ");
		for (int i = 0; i < transactionsUser1.length; ++i) {
			System.out.println(transactionsUser1[i].getRecipient().getName() + " " + transactionsUser1[i].getSender().getName() 
				+ " " + transactionsUser1[i].getTransferAmount() + " " + transactionsUser1[i].getTransferCategory());
		}

		System.out.println("\n user2 Transactions: ");
		for (int i = 0; i < transactionsUser2.length; ++i) {
			System.out.println(transactionsUser2[i].getRecipient().getName() + " " + transactionsUser2[i].getSender().getName() 
				+ " " + transactionsUser2[i].getTransferAmount() + " " + transactionsUser2[i].getTransferCategory());
		}

		System.out.println("\n user3 Transactions: ");
		for (int i = 0; i < transactionsUser3.length; ++i) {
			System.out.println(transactionsUser3[i].getRecipient().getName() + " " + transactionsUser3[i].getSender().getName() 
				+ " " + transactionsUser3[i].getTransferAmount() + " " + transactionsUser3[i].getTransferCategory());
		}


		System.out.println("\n Remove user2 Nick-Mike -100 and Bob-Nick 5 Transaction: ");
		service.removeTransaction(transactionsUser2[0].getIdentifier(), transactionsUser2[0].getRecipient().getIdentifier());
		service.removeTransaction(transactionsUser2[1].getIdentifier(), transactionsUser2[1].getSender().getIdentifier());

		transactionsUser1 = service.getTransactions(user1.getIdentifier());
		transactionsUser2 = service.getTransactions(user2.getIdentifier());
		transactionsUser3 = service.getTransactions(user3.getIdentifier());


		System.out.println("\n user1 Transactions: ");
		for (int i = 0; i < transactionsUser1.length; ++i) {
			System.out.println(transactionsUser1[i].getRecipient().getName() + " " + transactionsUser1[i].getSender().getName() 
				+ " " + transactionsUser1[i].getTransferAmount() + " " + transactionsUser1[i].getTransferCategory());
		}

		System.out.println("\n user2 Transactions: ");
		for (int i = 0; i < transactionsUser2.length; ++i) {
			System.out.println(transactionsUser2[i].getRecipient().getName() + " " + transactionsUser2[i].getSender().getName() 
				+ " " + transactionsUser2[i].getTransferAmount() + " " + transactionsUser2[i].getTransferCategory());
		}

		System.out.println("\n user3 Transactions: ");
		for (int i = 0; i < transactionsUser3.length; ++i) {
			System.out.println(transactionsUser3[i].getRecipient().getName() + " " + transactionsUser3[i].getSender().getName() 
				+ " " + transactionsUser3[i].getTransferAmount() + " " + transactionsUser3[i].getTransferCategory());
		}



		try {
			service.performTransaction(user2.getIdentifier(), user3.getIdentifier(), 1000000);
		} catch (IllegalTransactionException e) {
			System.out.println("\n Exception! user3 have not 1000000, balance = " + service.getUserBalance(user3.getIdentifier()));
		}
	}
}