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
import hr.java.web.petkovic.moneyapp.trosak.Novcanik;

@RestController
@RequestMapping(path="/api/novcanik", produces="application/json")
@CrossOrigin
@Secured({"ROLE_USER"})
public class NovcanikRestController {

	private final HibernateTrosakRepository trosakRepo;
	private final HibernateNovcanikRepository novcanikRepo;

	public NovcanikRestController(HibernateTrosakRepository trosak, HibernateNovcanikRepository novcanik)
	{
		this.trosakRepo = trosak;
		this.novcanikRepo = novcanik;
	}

	@GetMapping
	public Iterable<Novcanik> findAll()
	{
		return novcanikRepo.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Novcanik> findOne(@PathVariable Long id)
	{
		Novcanik novcanik = novcanikRepo.findOne(id);
		if (novcanik == null)
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<Novcanik>(novcanik, HttpStatus.OK);
		}
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(consumes="application/json")
	public Novcanik save(@RequestBody Novcanik trosak)
	{
		return novcanikRepo.save(trosak);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Novcanik> update(@RequestBody Novcanik novcanik)
	{
		Novcanik nov = novcanikRepo.update(novcanik);
		if (nov == null)
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<Novcanik>(novcanik, HttpStatus.OK);
		}
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id)
	{
		novcanikRepo.delete(id);
	}
}
