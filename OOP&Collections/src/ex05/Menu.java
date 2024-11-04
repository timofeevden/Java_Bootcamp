package ex05;

import java.util.Scanner;
import java.util.UUID;

public class Menu {
	private TransactionsService service;
	private Scanner scanner;
	private boolean devMode;

	public Menu(boolean isDevMode) {
		service = new TransactionsService();
		scanner = new Scanner(System.in);
		devMode = isDevMode;
	}

	public void run() {
		while(true) {
			printMenuList();
			selectMenu(inputOption());
		}
	}

	public void printMenuList() {
		System.out.println("1. Add a user");
		System.out.println("2. View user balances");
		System.out.println("3. Perform a transfer");
		System.out.println("4. View all transactions for a specific user");

		if (devMode == true) {
			System.out.println("5. DEV - remove a transfer by ID");
			System.out.println("6. DEV - check transfer validity");
			System.out.println("7. Finish execution");
		} else {
			System.out.println("5. Finish execution");
		}
	}

	public int inputOption() {
		System.out.print("-> ");
		int option = 0;

		try {
			checkNextIntInput();
			option = scanner.nextInt();
		} catch (Exception e) {
			option = 0;
		}

		return option;
	}

	public void checkEmptyInput() {
		if (scanner.hasNext() == false) {
			System.err.println("\nInput is empty! Exit.");
			scanner.close();
			System.exit(0);
		}
	}

	public void checkNextIntInput() {
		checkEmptyInput();
		if (scanner.hasNextInt() == false) {
			scanner.nextLine();
			throw new IllegalArgumentException("Invalid input, waiting integer!");
		}
	}

	public void selectMenu(int option) {
		if (option == 1) {
			addUser();
		} else if (option == 2) {
			viewUserBalance();
		} else if (option == 3) {
			performTransfer();
		} else if (option == 4) {
			viewUserTransactions();
		} else if (option == 5 && devMode == false) {
			scanner.close();
			System.exit(0);
		} else if (option == 5 && devMode == true) {
			removeTransfer();
		} else if (option == 6 && devMode == true) {
			checkTransferValidity();
		} else if (option == 7 && devMode == true) {
			scanner.close();
			System.exit(0);
		} else {
			System.out.println("Incorrect option!");
			System.out.println("---------------------------------------------------------");
		}

	}

	public void addUser() {
		System.out.println("Enter a username and a balance");
		System.out.print("-> ");
		try {
			checkEmptyInput();
			String name = scanner.next();
			checkNextIntInput();
			int balance = scanner.nextInt();
			scanner.nextLine();
			if (balance < 0) {
				System.out.println("Start balance can't be < 0. New balance = 0");
			}
			User newUser = new User(name, balance);
			service.addUser(newUser);
			System.out.println("User with id = " + newUser.getIdentifier() + " is added");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("---------------------------------------------------------");
		}
	}

	public void viewUserBalance() {
		System.out.println("Enter a user ID");
		System.out.print("-> ");
		try {
			checkNextIntInput();
			int id = scanner.nextInt();
			scanner.nextLine();
			String name = service.getUserName(id);
			int balance = service.getUserBalance(id);
			System.out.println(name + " - " + balance);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("---------------------------------------------------------");
		}
	}

	public void performTransfer() {
		System.out.println("Enter a sender ID, a recepient ID, and a transfer amount");
		System.out.print("-> ");
		try {
			checkNextIntInput();
			int senderId = scanner.nextInt();
			checkNextIntInput();
			int recepientId = scanner.nextInt();
			checkNextIntInput();
			int transferAmount = scanner.nextInt();
			scanner.nextLine();
			service.performTransaction(recepientId, senderId, transferAmount);
			System.out.println("The transfer is completed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("---------------------------------------------------------");
		}
	}

	public void viewUserTransactions() {
		System.out.println("Enter a user ID");
		System.out.print("-> ");
		try {
			checkNextIntInput();
			int id = scanner.nextInt();
			scanner.nextLine();
			Transaction[] transactions = service.getTransactions(id);

			for(Transaction pay : transactions) {
				if (pay.getTransferCategory() == Transaction.TransferCategory.CREDIT) {
					System.out.print("To " + pay.getSender().getName() + "(id = " + pay.getSender().getIdentifier() + ") " );
				} else {
					System.out.print("From " + pay.getSender().getName() + "(id = " + pay.getSender().getIdentifier() + ") " );
				}
				System.out.println(pay.getTransferAmount() + " with id = " + pay.getIdentifier());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("---------------------------------------------------------");
		}
	}

	public void removeTransfer() {
		System.out.println("Enter a user ID and a transfer ID");
		System.out.print("-> ");
		try {
			checkNextIntInput();
			int userId = scanner.nextInt();
			checkEmptyInput();
			String tansactionId = scanner.next();
			scanner.nextLine();
			UUID transId = UUID.fromString(tansactionId);
			Transaction[] transactions = service.getTransactions(userId);

			if (transactions.length == 0) {
				System.err.println("Can't find transaction with id = " + transId);
			}

			for(Transaction tr : transactions) {
				if (tr.getIdentifier().equals(transId)) {
					System.out.print("Transfer ");
					String nameAndId;
					if (tr.getTransferCategory() == Transaction.TransferCategory.DEBIT) {
						System.out.print("From ");
						nameAndId = tr.getRecipient().getName() + "(id = " + tr.getRecipient().getIdentifier() + ") ";
					} else {
						System.out.print("To ");
						nameAndId = tr.getSender().getName() + "(id = " + tr.getSender().getIdentifier() + ") ";
					}
					System.out.println(nameAndId + Math.abs(tr.getTransferAmount()) + " removed");
					service.removeTransaction(transId, userId);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("---------------------------------------------------------");
		}
	}

	public void checkTransferValidity() {
		try {
			Transaction[] unpair = service.checkValidityTransackions();
			System.out.println("Check results:");

			if (unpair.length == 0) {
				System.out.println("All transactions are correctly");
			}

			for (Transaction tr : unpair) {
				String nameAndId = tr.getRecipient().getName() + "(id = " + tr.getRecipient().getIdentifier() + ") ";
				System.out.print(nameAndId + "has unacknowledged transfer id = " + tr.getIdentifier());

				if (tr.getTransferCategory() == Transaction.TransferCategory.DEBIT) {
					System.out.print(" from ");
				} else {
					System.out.print(" to ");
				}
				nameAndId = tr.getSender().getName() + "(id = " + tr.getSender().getIdentifier() + ") ";
				System.out.println(nameAndId + "for " + Math.abs(tr.getTransferAmount()));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("---------------------------------------------------------");
		}
	}

}