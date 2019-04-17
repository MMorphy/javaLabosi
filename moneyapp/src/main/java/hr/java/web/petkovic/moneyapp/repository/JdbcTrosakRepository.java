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

import hr.java.web.petkovic.moneyapp.trosak.Trosak;

@Repository
public class JdbcTrosakRepository implements TrosakRepository{
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert trosakInserter;

	@Autowired
	public JdbcTrosakRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.trosakInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("trosak").usingGeneratedKeyColumns("id");
	}

	@Override
	public Iterable<Trosak> findAll() {
		return jdbcTemplate.query("select * from trosak", this::mapRowToTrosak);
	}
	
	@Override
	public Iterable<Trosak> findAllInNovcanik(Long id) {
		return jdbcTemplate.query("select * from trosak where novcanik_id = ?", this::mapRowToTrosak, id);
	}

	@Override
	public Trosak findOne(Long id) {
		return jdbcTemplate.queryForObject("select * from trosak where id = ?", this::mapRowToTrosak, id);
	}
	
	@Override
	public void deleteByNovcanik(Long id) {
		jdbcTemplate.update("delete from trosak where novcanik_id = ?", id);
	}

	@Override
	public Trosak save(Trosak trosak) {
		trosak.setId(saveTrosakDetails(trosak));
		return trosak;
	}
	
	private long saveTrosakDetails(Trosak trosak) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("id", trosak.getId());
		values.put("naziv", trosak.getNaziv());
		values.put("iznos", trosak.getIznos());
		values.put("createdate", LocalDateTime.now());
		values.put("vrstaTroska", trosak.getVrstaTroska().toString());
		values.put("novcanik_id", trosak.getNovcanikId());
		return trosakInserter.executeAndReturnKey(values).longValue();
	}

	private Trosak mapRowToTrosak(ResultSet rs, int rowNum) throws SQLException {
		Trosak trosak = new Trosak();
		
		trosak.setId(rs.getLong("id"));
		trosak.setNaziv(rs.getString("naziv"));
		trosak.setIznos(rs.getDouble("iznos"));
		trosak.setVrstaTroska(Trosak.VrstaTroska.valueOf(rs.getString("vrstatroska")));
		trosak.setNovcanikId(rs.getLong("novcanik_id"));
		
		return trosak;
	}
}
