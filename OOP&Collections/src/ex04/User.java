package ex04;

public class User {
	private final Integer identifier;
	private String name;
	private Integer balance;
	private TransactionsList transactionsList;

	public User(String name, Integer balance) {
		this.identifier = UserIdsGenerator.getInstance().generateId();
		this.name = name;
		this.setBalance(balance);
		transactionsList = new TransactionsLinkedList();
	}
	
	public User(String name) {
		this.identifier = UserIdsGenerator.getInstance().generateId();
		this.name = name;
		this.balance = 0;
		transactionsList = new TransactionsLinkedList();
	}

	public void setBalance(Integer newBalance) {
		if (newBalance < 0) {
			balance = 0;
		} else {
			balance = newBalance;
		}
	}

	public void setName(String newName) {
		name = newName;
	}

	public Integer getIdentifier() {
		return identifier;
	}

	public Integer getBalance() {
		return balance;
	}

	public String getName() {
		return name;
	}

	public TransactionsList getTransactionsList() {
		return transactionsList;
	}
}