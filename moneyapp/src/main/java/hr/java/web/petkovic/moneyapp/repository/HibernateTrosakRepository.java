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

@Primary
@Repository
@Transactional
public class HibernateTrosakRepository implements TrosakRepository {

	private final EntityManager em;

	private Session sessionFactory() {
		return (Session) em.getDelegate();
	}

	@Autowired
	public HibernateTrosakRepository(EntityManager em) {
		this.em = em;
	}

	@Override
	public Iterable<Trosak> findAll() {
		return sessionFactory().createQuery("SELECT t FROM Trosak t", Trosak.class).getResultList();
	}

	@Override
	public Iterable<Trosak> findAllInNovcanik(Long id) {
		return sessionFactory().createQuery("SELECT t FROM Trosak t WHERE t.novcanikId = :id", Trosak.class)
				.setParameter("id", id).getResultList();
	}

	@Override
	public Trosak findOne(Long id) {
		return sessionFactory().find(Trosak.class, id);
	}

	@Override
	public void deleteByNovcanik(Long id) {
		List<Trosak> troskovi = (List<Trosak>) findAllInNovcanik(id);
		for (Trosak trosak : troskovi) {
			sessionFactory().delete(trosak);
		}
	}

	@Override
	public Trosak save(Trosak trosak) {
		trosak.setCreateDate(LocalDateTime.now());
		Serializable id = sessionFactory().save(trosak);
		trosak.setId((Long) id);
		return trosak;
	}

}