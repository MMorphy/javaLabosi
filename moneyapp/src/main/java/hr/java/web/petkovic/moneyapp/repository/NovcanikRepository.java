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

	List<Novcanik> findAllByUser_UsernameAndListaTroskova_IznosGreaterThanEqual(String username, Double iznos);

	List<Novcanik> findAllByUser_UsernameAndListaTroskova_IznosLessThanEqual(String username, Double iznos);

	List<Novcanik> findAllByUser_UsernameAndListaTroskova_IznosGreaterThanEqualAndListaTroskova_IznosLessThanEqual(String username, Double iznosManji, Double iznosVeci);

	List<Novcanik> findAllByUser_Username(String korisnicko);

	List<Novcanik> findAllByListaTroskova_IznosGreaterThanEqual(double parseDouble);

	List<Novcanik> findAllByListaTroskova_IznosLessThanEqual(double parseDouble);

	List<Novcanik> findAllByListaTroskova_IznosGreaterThanEqualAndListaTroskova_IznosLessThanEqual(double parseDouble,
			double parseDouble2);
}
