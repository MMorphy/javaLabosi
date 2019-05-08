package hr.java.web.petkovic.moneyapp.rest;

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

import hr.java.web.petkovic.moneyapp.repository.HibernateNovcanikRepository;
import hr.java.web.petkovic.moneyapp.repository.HibernateTrosakRepository;
import hr.java.web.petkovic.moneyapp.trosak.Trosak;

@RestController
@RequestMapping(path="/api/trosak", produces="application/json")
@CrossOrigin
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class TrosakRestController {

	private final HibernateTrosakRepository trosakRepo;
	private final HibernateNovcanikRepository novcanikRepo;

	public TrosakRestController(HibernateTrosakRepository trosak, HibernateNovcanikRepository novcanik)
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
		Trosak trosak = trosakRepo.findOne(id);
		if (trosak == null)
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<Trosak>(trosak, HttpStatus.OK);
		}
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(consumes="application/json")
	public Trosak save(@RequestBody Trosak trosak)
	{
		return trosakRepo.save(trosak);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Trosak> update(@PathVariable Long id, @RequestBody Trosak trosak)
	{
		Trosak tr = trosakRepo.update(trosak, id);
		return new ResponseEntity<Trosak>(tr, HttpStatus.OK);

	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id)
	{
		trosakRepo.delete(id);
	}
}
