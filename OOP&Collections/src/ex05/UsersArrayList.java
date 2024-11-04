package ex05;

public class UsersArrayList implements UsersList {
	private Integer capacity = 10;
	private Integer numberOfUsers = 0;
	private User[] users = new User[capacity];

	public UsersArrayList() {}

	public Integer getCapacity() {
		return capacity;
	}

	@Override
	public void addUser(User newUser) {
		if (numberOfUsers == capacity) {
			capacity *= 2;
			User[] tmp = new User[capacity];
			System.arraycopy(users, 0, tmp, 0, numberOfUsers);
			users = tmp;
		}
		users[numberOfUsers++] = newUser;
	}

	@Override
	public User getUserById(Integer id) {
		for(int i = 0; i < numberOfUsers; ++i) {
			if (users[i].getIdentifier() == id) {
				return users[i];
			}
		}
		throw new UserNotFoundException("User Not Found with id = " + id);
	}

	@Override
	public User getUserByIndex(Integer index) {
		if (index < 0 || index >= numberOfUsers) {
			throw new UserNotFoundException("User Not Found with index = " + index);
		}
		return users[index];
	}

	@Override
	public Integer getNumberOfUsers() {
		return numberOfUsers;
	}


}