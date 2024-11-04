package ex00;

import java.util.UUID;

public class Transaction {

	public enum TransferCategory {
		DEBIT, CREDIT
	}

	private final UUID identifier;
	private User recipient;
	private User sender;
	private TransferCategory transferCategory;
	private Integer transferAmount;

	public Transaction(User recipient, User sender, TransferCategory transferCategory, Integer transferAmount) {
		if (recipient.getIdentifier() == sender.getIdentifier()) {
			errorMessage("Error! Recipient and Sender are equals");
		} else if (recipient.getBalance() < 0 || sender.getBalance() < 0) {
			errorMessage("Error! Balance < 0 !");
		}

		this.identifier = UUID.randomUUID();
		this.recipient = recipient;
		this.sender = sender;
		this.transferCategory = transferCategory;

		if ((transferCategory == Transaction.TransferCategory.DEBIT && (transferAmount < 0 || sender.getBalance() < transferAmount))
			|| (transferCategory == Transaction.TransferCategory.CREDIT && (transferAmount > 0 || recipient.getBalance() < -transferAmount))) {
			this.transferAmount = 0;
		} else {
			this.transferAmount = transferAmount;
		}

		sender.setBalance(sender.getBalance() - this.transferAmount);
		recipient.setBalance(recipient.getBalance() + this.transferAmount);
	}

	public void setRecipient(User newRecipient) {
		recipient = newRecipient;
	}

	public void setSender(User newSender) {
		sender = newSender;
	}

	public void setTransferCategory(TransferCategory newCategory) {
		transferCategory = newCategory;
	}

	public void setTransferAmount(Integer newTransferAmount) {
		transferAmount = newTransferAmount;
	}

	public UUID getIdentifier() {
		return identifier;
	}

	public User getRecipient() {
		return recipient;
	}

	public User getSender() {
		return sender;
	}

	public TransferCategory getTransferCategory() {
		return transferCategory;
	}

	public Integer getTransferAmount() {
		return transferAmount;
	}

	private void errorMessage(String message) {
		System.err.println(message);
		System.exit(-1);
	}
}