package hr.java.web.petkovic.moneyapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import hr.java.web.petkovic.moneyapp.trosak.Trosak;

public interface TrosakRepository extends CrudRepository<Trosak, Long>{

	Iterable<Trosak> findAll();

	Optional<Trosak> findById(Long id);

	void deleteById(Long id);

	Trosak save(Trosak trosak);

	List<Trosak> findByNazivLike(String trosakNaziv);

	List<Trosak> findByNazivLikeAndNovcanikIdIn(String naziv, List<Long> id);

	void deleteByNovcanikId(Long novcanikId);

	List<Trosak> findByNovcanikId(Long novcanikId);
}
