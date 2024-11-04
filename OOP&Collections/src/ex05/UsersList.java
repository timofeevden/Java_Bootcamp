package ex05;

public interface UsersList {
	public void addUser(User newUser);

	public User getUserById(Integer id);

	public User getUserByIndex(Integer index);

	public Integer getNumberOfUsers();
}