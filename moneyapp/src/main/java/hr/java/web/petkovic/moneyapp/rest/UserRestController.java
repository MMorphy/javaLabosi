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

import hr.java.web.petkovic.moneyapp.repository.HibernateUserRepository;
import hr.java.web.petkovic.moneyapp.trosak.Trosak;
import hr.java.web.petkovic.moneyapp.trosak.User;

@RestController
@RequestMapping(path="/api/user", produces="application/json")
@CrossOrigin
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class UserRestController {

	private final HibernateUserRepository userRepo;

	public UserRestController(HibernateUserRepository repo)
	{
		this.userRepo = repo;
	}

	@GetMapping
	public Iterable<User> findAll()
	{
		return userRepo.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> findOne(@PathVariable Long id)
	{
		User user = userRepo.findOne(id);
		if (user == null)
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(consumes="application/json")
	public User save(@RequestBody User user)
	{
		return userRepo.save(user);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user)
	{
		User usr = userRepo.update(user, id);
		return new ResponseEntity<User>(usr, HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id)
	{
		userRepo.delete(id);
	}
}
