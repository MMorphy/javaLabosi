package hr.java.web.petkovic.moneyapp.repository;

import hr.java.web.petkovic.moneyapp.trosak.Trosak;

public interface TrosakRepository {

	public Iterable<Trosak> findAll();
	
	public Iterable<Trosak> findAllInNovcanik(Long id);

	public Trosak findOne(Long id);
	
	public void deleteByNovcanik(Long id);

	public Trosak save(Trosak trosak);
}
