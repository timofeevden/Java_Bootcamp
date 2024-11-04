package ex04;

import java.util.UUID;

public class TransactionsService {
	private UsersList users;

	public TransactionsService() {
		users = new UsersArrayList();
	}

	public void addUser(User newUser) {
		users.addUser(newUser);
	}

	public Integer getUserBalance(Integer id) {
		return users.getUserById(id).getBalance();
	}

	public Integer getUserBalance(User user) {
		return users.getUserById(user.getIdentifier()).getBalance();
	}

	public Transaction[] getTransactions(Integer userId) {
		return users.getUserById(userId).getTransactionsList().toArray();
	}

	public Transaction[] getTransactions(User user) {
		return users.getUserById(user.getIdentifier()).getTransactionsList().toArray();
	}

	public void performTransaction(User recipient, User sender, Integer amount) {
		performTransaction(recipient.getIdentifier(), sender.getIdentifier(), amount);
	}

	public Integer getNumberOfUsers() {
		return users.getNumberOfUsers();
	}

	public void performTransaction(Integer recipientId, Integer senderId, Integer amount) {
		User sender = users.getUserById(senderId);
		User recipient = users.getUserById(recipientId);
		Transaction.TransferCategory recipientCategory = Transaction.TransferCategory.DEBIT;
		Transaction.TransferCategory senderCategory = Transaction.TransferCategory.CREDIT;

		if (amount < 0) {
			recipientCategory = Transaction.TransferCategory.CREDIT;
			senderCategory = Transaction.TransferCategory.DEBIT;
		}
		Transaction transactionRecipient = new Transaction(recipient, sender, recipientCategory, amount);
		Transaction transactionSender = new Transaction(sender, recipient, senderCategory, -amount);

		transactionSender.setIdentifier(transactionRecipient.getIdentifier());

		recipient.getTransactionsList().addTransaction(transactionRecipient);
		sender.getTransactionsList().addTransaction(transactionSender);

		recipient.setBalance(recipient.getBalance() + amount);
		sender.setBalance(sender.getBalance() - amount);
 	}

	public void removeTransaction(UUID transactionId, Integer userId) {
		users.getUserById(userId).getTransactionsList().removeTransaction(transactionId);
	}

	public Transaction[] checkValidityTransackions() {
		TransactionsList unpairs = new TransactionsLinkedList();

		//	перебираем юзеров
		for (int i = 0; i < users.getNumberOfUsers(); ++i) {
			User currentUser = users.getUserByIndex(i);
			Transaction[] transactions = currentUser.getTransactionsList().toArray();
			
			for (int j = 0; j < transactions.length; ++j) {
				User secondUser = transactions[j].getSender();
				Transaction[] transactionsSecond = secondUser.getTransactionsList().toArray();
				boolean wasFound = false;

				for (int k = 0; k < transactionsSecond.length; ++k) {
					if (transactions[j].getIdentifier() == transactionsSecond[k].getIdentifier()) {
						wasFound = true;
						break;
					}
				}
				if (wasFound == false) {
					unpairs.addTransaction(transactions[j]);
				}
			}
		}

		return unpairs.toArray();
	}



}