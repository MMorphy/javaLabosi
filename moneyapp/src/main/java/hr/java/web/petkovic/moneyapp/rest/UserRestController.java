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

import hr.java.web.petkovic.moneyapp.repository.UserRepository;
import hr.java.web.petkovic.moneyapp.trosak.User;

@RestController
@RequestMapping(path="/api/user", produces="application/json")
@CrossOrigin
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class UserRestController {

	@Autowired
	private final UserRepository userRepo;

	public UserRestController(UserRepository repo)
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
		Optional<User> user = userRepo.findById(id);
		if (user.isEmpty())
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<User>(user.get(), HttpStatus.OK);
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
		Optional<User> optUser = userRepo.findById(id);
		if (optUser.isPresent())
		{
			User usr = optUser.get();
			usr.setEnabled(user.getEnabled());
			usr.setPassword(user.getPassword());
			usr.setUsername(user.getPassword());
			userRepo.save(usr);
			return new ResponseEntity<User>(usr, HttpStatus.OK);
		}
		else
		{
			userRepo.save(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id)
	{
		userRepo.deleteById(id);
	}
}
