package hr.java.web.petkovic.moneyapp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import hr.java.web.petkovic.moneyapp.trosak.User;

public interface UserRepository extends CrudRepository<User, Long>{

	Iterable<User> findAll();

	Optional<User> findById(Long id);

	void deleteById(Long id);

	User save(User user);

	User findByUsername(String username);
}
