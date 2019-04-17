package hr.java.web.petkovic.moneyapp.repository;

import hr.java.web.petkovic.moneyapp.trosak.User;

public interface UserRepository {

	public Iterable<User> findAll();

	public User findOne(Long id);

	public void deleteById(Long id);

	public User save(User user);

	public User findByName(String name);
}
