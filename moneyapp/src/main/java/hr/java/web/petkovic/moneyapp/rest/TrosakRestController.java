package hr.java.web.petkovic.moneyapp.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hr.java.web.petkovic.moneyapp.repository.NovcanikRepository;
import hr.java.web.petkovic.moneyapp.repository.TrosakRepository;
import hr.java.web.petkovic.moneyapp.trosak.Trosak;

@RestController
@RequestMapping(path="/api/trosak", produces="application/json")
@CrossOrigin
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class TrosakRestController {

	private static Logger logger = LoggerFactory.getLogger(TrosakRestController.class);
	@Autowired
	private final TrosakRepository trosakRepo;
	@Autowired
	private final NovcanikRepository novcanikRepo;

	public TrosakRestController(TrosakRepository trosak, NovcanikRepository novcanik)
	{
		this.trosakRepo = trosak;
		this.novcanikRepo = novcanik;
	}

	@GetMapping
	public Iterable<Trosak> findAll()
	{
		return trosakRepo.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Trosak> findOne(@PathVariable Long id)
	{
		Optional<Trosak> trosak = trosakRepo.findById(id);
		if (trosak.isEmpty())
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<Trosak>(trosak.get(), HttpStatus.OK);
		}
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(consumes="application/json")
	public Trosak save( Trosak trosak)
	{
		return trosakRepo.save(trosak);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Trosak> update(@PathVariable Long id, Trosak trosak)
	{
		Optional<Trosak> tr = trosakRepo.findById(id);
		if (tr.isPresent())
		{
			Trosak tro = tr.get();
			tro.setCreateDate(trosak.getCreateDate());
			tro.setIznos(trosak.getIznos());
			tro.setNaziv(trosak.getNaziv());
			tro.setNovcanikId(trosak.getNovcanikId());
			tro.setVrstaTroska(trosak.getVrstaTroska());
			trosakRepo.save(tro);
			return new ResponseEntity<Trosak>(tro, HttpStatus.OK);
		}
		else
		{
			trosakRepo.save(trosak);
			return new ResponseEntity<Trosak>(trosak, HttpStatus.OK);
		}

	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id)
	{
		trosakRepo.deleteById(id);
	}
}
