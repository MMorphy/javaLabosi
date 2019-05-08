package hr.java.web.petkovic.moneyapp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import hr.java.web.petkovic.moneyapp.trosak.Novcanik;

public class JdbcNovcanikRepository{
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert novcanikInserter;

	public JdbcNovcanikRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.novcanikInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("novcanik").usingGeneratedKeyColumns("id");
	}

	public Iterable<Novcanik> findAll() {
		return jdbcTemplate.query("select * from novcanik", this::mapRowToNovcanik);
	}

	public Novcanik findOne(Long id) {
		return jdbcTemplate.queryForObject("Select * from novcanik where id = ?", this::mapRowToNovcanik, id);
	}
	
	public Novcanik findByUsernameId(Long usernameId) {
		return jdbcTemplate.queryForObject("Select * from novcanik n where username_id = ?", this::mapRowToNovcanik, usernameId);
	}

	public Novcanik save(Novcanik novcanik) {
		novcanik.setId(saveTrosakDetails(novcanik));
		return novcanik;
	}
	
	private long saveTrosakDetails(Novcanik novcanik) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("id", novcanik.getId());
		values.put("naziv", novcanik.getIme());
		values.put("createdate", LocalDateTime.now());
		values.put("tipnovcanika", novcanik.getTipNovcanika().toString());
//		values.put("username_id", novcanik.getUsernameId());
		return novcanikInserter.executeAndReturnKey(values).longValue();
	}

	private Novcanik mapRowToNovcanik(ResultSet rs, int rowNum) throws SQLException {
		Novcanik trosak = new Novcanik();
		
		trosak.setId(rs.getLong("id"));
		trosak.setIme(rs.getString("ime"));
		trosak.setTipNovcanika(Novcanik.TipNovcanika.valueOf(rs.getString("tipnovcanika")));
//		trosak.setUsernameId(rs.getLong("username_id"));
		
		return trosak;
	}
}
