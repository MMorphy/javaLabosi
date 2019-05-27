package hr.java.web.petkovic.moneyapp.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
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

import hr.java.web.petkovic.moneyapp.repository.NovcanikRepository;
import hr.java.web.petkovic.moneyapp.repository.TrosakRepository;
import hr.java.web.petkovic.moneyapp.trosak.Novcanik;

@RestController
@RequestMapping(path="/api/novcanik", produces="application/json")
@CrossOrigin
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class NovcanikRestController {

	@Autowired
	private final TrosakRepository trosakRepo;
	@Autowired
	private final NovcanikRepository novcanikRepo;

	public NovcanikRestController(TrosakRepository trosak, NovcanikRepository novcanik)
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
		Optional<Novcanik> novcanik = novcanikRepo.findById(id);
		if (novcanik.isEmpty())
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<Novcanik>(novcanik.get(), HttpStatus.OK);
		}
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(consumes="application/json")
	public Novcanik save(Novcanik trosak)
	{
		return novcanikRepo.save(trosak);
	}

	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<Novcanik> update(@PathVariable Long id, Novcanik novcanik)
	{
		Optional<Novcanik> optNovcanik= novcanikRepo.findById(id);
		if (optNovcanik.isPresent())
		{
			Novcanik nov = optNovcanik.get();
			nov.setCreateDate(novcanik.getCreateDate());
			nov.setIme(novcanik.getIme());
			nov.setTipNovcanika(novcanik.getTipNovcanika());
			nov.setUser(novcanik.getUser());
			nov.setListaTroskova(novcanik.getListaTroskova());
			novcanikRepo.save(nov);
			return new ResponseEntity<Novcanik>(nov, HttpStatus.OK);
		}
		else
		{
			novcanikRepo.save(novcanik);
			return new ResponseEntity<Novcanik>(novcanik, HttpStatus.OK);
		}
	}

	@Transactional
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id)
	{
		novcanikRepo.deleteById(id);
	}
}
