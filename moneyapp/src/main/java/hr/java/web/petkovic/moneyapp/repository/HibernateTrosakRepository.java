package hr.java.web.petkovic.moneyapp.repository;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import hr.java.web.petkovic.moneyapp.trosak.Trosak;

public class HibernateTrosakRepository {

	private final EntityManager em;

	private Session sessionFactory() {
		return (Session) em.getDelegate();
	}

	@Autowired
	public HibernateTrosakRepository(EntityManager em) {
		this.em = em;
	}

	public Iterable<Trosak> findAll() {
		return sessionFactory().createQuery("SELECT t FROM Trosak t", Trosak.class).getResultList();
	}

	public Iterable<Trosak> findAllInNovcanik(Long id) {
		return sessionFactory().createQuery("SELECT t FROM Trosak t WHERE t.novcanikId = :id", Trosak.class)
				.setParameter("id", id).getResultList();
	}

	public Trosak findOne(Long id) {
		return sessionFactory().find(Trosak.class, id);
	}

	public void deleteByNovcanik(Long id) {
		List<Trosak> troskovi = (List<Trosak>) findAllInNovcanik(id);
		for (Trosak trosak : troskovi) {
			sessionFactory().delete(trosak);
		}
	}

	public Trosak save(Trosak trosak) {
		trosak.setCreateDate(LocalDateTime.now());
		Serializable id = sessionFactory().save(trosak);
		trosak.setId((Long) id);
		return trosak;
	}

	public Trosak update(Trosak trosak, Long id)
	{
		Trosak tr = findOne(id);
		if (tr != null)
		{
			tr.setCreateDate(trosak.getCreateDate());
			tr.setIznos(trosak.getIznos());
			tr.setNaziv(trosak.getNaziv());
			tr.setNovcanikId(trosak.getNovcanikId());
			tr.setVrstaTroska(trosak.getVrstaTroska());
			sessionFactory().save(tr);
			return tr;
		}
		else
		{
			sessionFactory().save(trosak);
			return trosak;
		}
	}
	public void delete(Long id)
	{
		Trosak trosak = findOne(id);
		sessionFactory().delete(trosak);
	}

}
