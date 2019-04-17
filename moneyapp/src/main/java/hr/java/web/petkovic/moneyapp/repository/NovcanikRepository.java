package hr.java.web.petkovic.moneyapp.repository;

import hr.java.web.petkovic.moneyapp.trosak.Novcanik;

public interface NovcanikRepository {
	public Iterable<Novcanik> findAll();

	public Novcanik findOne(Long id);

	public Novcanik findByUsername(String username);

	public Novcanik save(Novcanik novcanik);

}
