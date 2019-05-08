package hr.java.web.petkovic.moneyapp.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import hr.java.web.petkovic.moneyapp.trosak.User;

public class HibernateUserRepository{

	private final EntityManager em;

	private Session sessionFactory() {
		return (Session) em.getDelegate();
	}

	public HibernateUserRepository(EntityManager em) {
		this.em = em;
	}

	public Iterable<User> findAll() {
		return sessionFactory().createQuery("SELECT u FROM User u", User.class).getResultList();
	}

	public User findOne(Long id) {
		return sessionFactory().find(User.class, id);
	}

	public void deleteById(Long id) {
		User user = findOne(id);
		sessionFactory().delete(user);
	}

	public User save(User user) {
		Serializable id = sessionFactory().save(user);
		user.setId((Long)id);
		return user;
	}

	public User findByName(String name) {
		return (User)sessionFactory().createQuery("SELECT u FROM User u WHERE u.username = :name", User.class).setParameter("name", name).getResultList().get(0);
	}

	public User update(User user, Long id)
	{
		User usr = findOne(id);
		if(usr != null)
		{
			usr.setEnabled(user.getEnabled());
			usr.setUsername(user.getUsername());
			sessionFactory().save(usr);
			return usr;
		}
		else
		{
			sessionFactory().save(user);
			return user;
		}
	}

	public void delete(Long id)
	{
		User user = findOne(id);
		sessionFactory().delete(user);
	}

}
