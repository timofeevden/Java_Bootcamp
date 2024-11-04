package ex03;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
	private Node first;
	private Node last;
	private int size;


	public TransactionsLinkedList() {
		first = null;
		last = null;
		size = 0;
	}

	@Override
	public void addTransaction(Transaction newTransaction) {
		Node newNode = new Node(newTransaction);
		newNode.prev = last;
		newNode.next = null;

		if (size == 0) {
			first = newNode;
		} else {
			last.next = newNode;
		}
		last = newNode;
		++size;

	}

	@Override
	public void removeTransaction(UUID id) {
		for (Node current = first; current != null; current = current.next) {
			if (current.transaction.getIdentifier() == id) {
				if (current == first) {
					first = first.next;
					if (first != null) {
						first.prev = null;
					}
				} else if (current == last) {
					last = last.prev;
				} else {
					current.prev.next = current.next;
					current.next.prev = current.prev;
				}
				--size;
				return;
			}
		}
		throw new TransactionNotFoundException("Transaction whith ID = " + id + " not found!");
	}

	@Override
	public Transaction[] toArray() {
		Transaction[] result = new Transaction[size];
		Node current = first;

		for(int i = 0; i < size && current != null; ++i) {
			result[i] = current.transaction;
			current = current.next;
		}

		return result;
	}


	private static class Node {
		public Node next;
		public Node prev;
		public Transaction transaction;

		public Node(Transaction transaction) {
			this.next = null;
			this.prev = null;
			this.transaction = transaction;
		}
	}
}