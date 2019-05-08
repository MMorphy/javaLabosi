package hr.java.web.petkovic.moneyapp.repository;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import hr.java.web.petkovic.moneyapp.trosak.Novcanik;
import hr.java.web.petkovic.moneyapp.trosak.Trosak;

@Primary
@Repository
@Transactional
public class HibernateNovcanikRepository implements NovcanikRepository {

	private final EntityManager em;

	private Session sessionFactory() {
		return (Session) em.getDelegate();
	}

	@Autowired
	public HibernateNovcanikRepository(EntityManager em) {
		this.em = em;
	}

	@Override
	public Iterable<Novcanik> findAll() {
		return sessionFactory().createQuery("SELECT n FROM Novcanik n", Novcanik.class).getResultList();
	}

	@Override
	public Novcanik findOne(Long id) {
		return sessionFactory().find(Novcanik.class, id);
	}

	@Override
	public Novcanik findByUsernameId(Long usernameId) {
		return (Novcanik)sessionFactory().createQuery("SELECT n FROM Novcanik n WHERE n.user.id = :id").setParameter("id", usernameId).getResultList().get(0);
	}

	public Iterable<Novcanik> findAllByUsernameId(Long usernameId)
	{
		return sessionFactory().createQuery("SELECT n FROM Novcanik n WHERE n.user.id = :id").setParameter("id", usernameId).getResultList();
	}

	@Override
	public Novcanik save(Novcanik novcanik) {
		novcanik.setCreateDate(LocalDateTime.now());
		Serializable id = sessionFactory().save(novcanik);
		novcanik.setId((Long)id);
		return novcanik;
	}


	public Novcanik update(Novcanik novcanik, Long id)
	{
		Novcanik nov = findOne(id);
		if (nov != null)
		{
			nov.setCreateDate(novcanik.getCreateDate());
			nov.setIme(novcanik.getIme());
			nov.setTipNovcanika(novcanik.getTipNovcanika());
			nov.setUser(novcanik.getUser());
			sessionFactory().save(nov);
			return nov;
		}
		else
		{
			sessionFactory().save(novcanik);
			return novcanik;
		}
	}

	public void delete(Long id)
	{
		Novcanik novcanik = findOne(id);
		sessionFactory().delete(novcanik);
	}

}
