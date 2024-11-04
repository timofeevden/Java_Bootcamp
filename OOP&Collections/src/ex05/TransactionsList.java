package ex05;

import java.util.UUID;

public interface TransactionsList {
	public void addTransaction(Transaction newTransaction);

	public void removeTransaction(UUID id);

	public Transaction[] toArray();
}