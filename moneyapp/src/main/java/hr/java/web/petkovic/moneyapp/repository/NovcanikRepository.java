package hr.java.web.petkovic.moneyapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import hr.java.web.petkovic.moneyapp.trosak.Novcanik;
import hr.java.web.petkovic.moneyapp.trosak.User;

public interface NovcanikRepository extends CrudRepository<Novcanik, Long> {

	Iterable<Novcanik> findAll();

	Optional<Novcanik> findById(Long id);

	List<Novcanik> findByUser(User id);

	void deleteById(Long id);

	Novcanik save(Novcanik novcanik);

}
