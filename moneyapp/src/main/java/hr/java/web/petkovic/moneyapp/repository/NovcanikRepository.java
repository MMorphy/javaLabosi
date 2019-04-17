package hr.java.web.petkovic.moneyapp.repository;

import hr.java.web.petkovic.moneyapp.trosak.Novcanik;

public interface NovcanikRepository {
	public Iterable<Novcanik> findAll();

	public Novcanik findOne(Long id);

	public Novcanik findByUsernameId(Long usernameId);

	public Novcanik save(Novcanik novcanik);

}
