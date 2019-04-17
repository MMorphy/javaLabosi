package hr.java.web.petkovic.moneyapp.repository;

import hr.java.web.petkovic.moneyapp.trosak.Auth;

public interface AdminRepository {

	public Iterable<Auth> findAll();
	
	public Iterable<Auth> findAllWithRole(String role);

	public Auth findAllRolesOfUser(String user);
	
	public void deleteByUserAndRole(String user, String role);

	public Auth save(Auth user);
}
