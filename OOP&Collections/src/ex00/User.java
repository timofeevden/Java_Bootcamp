package ex00;

public class User {
	private final Integer identifier;
	private String name;
	private Integer balance;

	public User(Integer identifier, String name, Integer balance) {
		this.identifier = identifier;
		this.name = name;
		this.setBalance(balance);
	}

	public User(Integer identifier, String name) {
		this.identifier = identifier;
		this.name = name;
		this.balance = 0;
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
}