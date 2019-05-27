package hr.java.web.petkovic.moneyapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import hr.java.web.petkovic.moneyapp.trosak.Trosak;
import hr.java.web.petkovic.moneyapp.trosak.Trosak.VrstaTroska;

public interface TrosakRepository extends CrudRepository<Trosak, Long>{

	Iterable<Trosak> findAll();

	Optional<Trosak> findById(Long id);

	void deleteById(Long id);

	Trosak save(Trosak trosak);

	List<Trosak> findByNazivLike(String trosakNaziv);

	List<Trosak> findByNazivLikeAndNovcanikIdIn(String naziv, List<Long> id);

	void deleteByNovcanikId(Long novcanikId);

	List<Trosak> findByNovcanikId(Long novcanikId);

	@Query(value="SELECT sum(t.iznos) FROM Trosak t WHERE t.vrstaTroska = :vrsta", nativeQuery=true)
	Double getSumByVrsta(@Param("vrsta")String vrsta);

	@Query(value="SELECT min(t.iznos) FROM Trosak t WHERE t.vrstaTroska = :vrsta", nativeQuery=true)
	Double getMinByVrsta(@Param("vrsta")String vrsta);

	@Query(value="SELECT max(t.iznos) FROM Trosak t WHERE t.vrstaTroska = :vrsta", nativeQuery=true)
	Double getMaxByVrsta(@Param("vrsta")String vrsta);
}
