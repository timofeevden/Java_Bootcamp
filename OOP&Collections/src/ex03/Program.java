package ex03;

public class Program {

	public static void main(String[] args) {
		User user1 = new User("Mike", 100);
		User user2 = new User("Jhon", 200);

		TransactionsList myList = user1.getTransactionsList();

		Transaction transaction1 = new Transaction(user1, user2, Transaction.TransferCategory.DEBIT, 1);
		Transaction transaction2 = new Transaction(user1, user2, Transaction.TransferCategory.DEBIT, 2);
		Transaction transaction3 = new Transaction(user1, user2, Transaction.TransferCategory.DEBIT, 3);
		Transaction transaction4 = new Transaction(user1, user2, Transaction.TransferCategory.DEBIT, 4);

		myList.addTransaction(transaction1);
		myList.addTransaction(transaction2);
		myList.addTransaction(transaction3);
		myList.addTransaction(transaction4);
		
		Transaction[] arr = new Transaction[4];
		arr = myList.toArray();

		System.out.println("\nTransactions array:");
		for (int i = 0; i < arr.length; ++i) {
			System.out.println(arr[i].getTransferAmount());
		}

		myList.removeTransaction(transaction1.getIdentifier());
		arr = myList.toArray();
		System.out.println("\nTransactions array without first:");
		for (int i = 0; i < arr.length; ++i) {
			System.out.println(arr[i].getTransferAmount());
		}

		myList.removeTransaction(transaction2.getIdentifier());
		arr = myList.toArray();
		System.out.println("\nTransactions array without second:");
		for (int i = 0; i < arr.length; ++i) {
			System.out.println(arr[i].getTransferAmount());
		}

		myList.removeTransaction(transaction4.getIdentifier());
		arr = myList.toArray();
		System.out.println("\nTransactions array without last:");
		for (int i = 0; i < arr.length; ++i) {
			System.out.println(arr[i].getTransferAmount());
		}

		myList.addTransaction(transaction1);
		arr = myList.toArray();
		System.out.println("\nTransactions array with added first in the end:");
		for (int i = 0; i < arr.length; ++i) {
			System.out.println(arr[i].getTransferAmount());
		}

		myList.removeTransaction(transaction1.getIdentifier());
		myList.removeTransaction(transaction3.getIdentifier());
		arr = myList.toArray();
		System.out.println("\nTransactions array is empty:");
		for (int i = 0; i < arr.length; ++i) {
			System.out.println(arr[i].getTransferAmount());
		}


		myList.addTransaction(transaction4);
		arr = myList.toArray();
		System.out.println("\nTransactions array with added fourth:");
		for (int i = 0; i < arr.length; ++i) {
			System.out.println(arr[i].getTransferAmount());
		}


		try {
			myList.removeTransaction(transaction1.getIdentifier());
		} catch (TransactionNotFoundException e) {
			System.out.println("Cant find transaction1 in remove");
		}

	}
}