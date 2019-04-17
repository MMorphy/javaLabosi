package hr.java.web.petkovic.moneyapp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import hr.java.web.petkovic.moneyapp.trosak.Auth;

@Repository
public class JdbcAdminRepository implements AdminRepository{

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert usersInserter;

	@Autowired
	public JdbcAdminRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.usersInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("authorities");
	}

	private long saveAuthDetails(Auth auth) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("username", auth.getUser());
		values.put("authority", auth.getRole());
		return usersInserter.executeAndReturnKey(values).longValue();
	}

	private Auth mapRowToAuth(ResultSet rs, int rowNum) throws SQLException {
		Auth auth = new Auth();
		auth.setUser(rs.getString("username"));
		auth.setRole(rs.getString("authority"));
		return auth;
}

	@Override
	public Iterable<Auth> findAll() {
		return jdbcTemplate.query("select * from authorities", this::mapRowToAuth);
	}

	@Override
	public Iterable<Auth> findAllWithRole(String role) {
		return jdbcTemplate.query("select * from authorities where authority = ?", this::mapRowToAuth, role);
	}

	@Override
	public Auth findAllRolesOfUser(String user) {
		return jdbcTemplate.queryForObject("select * from authorities where username = ?", this::mapRowToAuth, user);

	}

	@Override
	public void deleteByUserAndRole(String user, String role) {
		jdbcTemplate.update("delete from authorities where username = ? and authority = ?", user, role);
	}

	@Override
	public Auth save(Auth user) {
		saveAuthDetails(user);
		return user;
	}
}