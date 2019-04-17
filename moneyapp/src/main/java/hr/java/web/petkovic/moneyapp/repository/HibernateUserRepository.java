package hr.java.web.petkovic.moneyapp.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import hr.java.web.petkovic.moneyapp.trosak.User;

@Primary
@Repository
@Transactional
public class HibernateUserRepository implements UserRepository {

	private final EntityManager em;

	private Session sessionFactory() {
		return (Session) em.getDelegate();
	}

	@Autowired
	public HibernateUserRepository(EntityManager em) {
		this.em = em;
	}

	@Override
	public Iterable<User> findAll() {
		return sessionFactory().createQuery("SELECT u FROM User u", User.class).getResultList();
	}

	@Override
	public User findOne(Long id) {
		return sessionFactory().find(User.class, id);
	}

	@Override
	public void deleteById(Long id) {
		User user = findOne(id);
		sessionFactory().delete(user);
	}

	@Override
	public User save(User user) {
		Serializable id = sessionFactory().save(user);
		user.setId((Long)id);
		return user;
	}

	@Override
	public User findByName(String name) {
		return (User)sessionFactory().createQuery("SELECT u FROM User u WHERE u.username = :name", User.class).setParameter("name", name).getResultList().get(0);
	}

}
