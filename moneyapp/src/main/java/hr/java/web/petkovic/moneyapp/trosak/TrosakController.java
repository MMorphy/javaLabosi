package hr.java.web.petkovic.moneyapp.trosak;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import hr.java.web.petkovic.moneyapp.repository.HibernateNovcanikRepository;
import hr.java.web.petkovic.moneyapp.repository.HibernateTrosakRepository;
import hr.java.web.petkovic.moneyapp.repository.HibernateUserRepository;

@Controller
@RequestMapping("/troskovi")
@SessionAttributes({ "vrste", "novcanik" })
public class TrosakController {
	private static Logger logger = LoggerFactory.getLogger(TrosakController.class);

	@Autowired
	public HibernateTrosakRepository trosakRepo;
	@Autowired
	public HibernateNovcanikRepository novcanikRepo;
	@Autowired
	public HibernateUserRepository userRepo;

	@GetMapping("/novitrosak")
	public String trosakForm(Model model) {

		model.addAttribute("trosak", new Trosak());
		logger.debug("Novi trosak u modelu");

		model.addAttribute("vrste", Trosak.VrstaTroska.values());

		logger.debug("Vrste troska u modelu");

		return "trosak";
	}

	@PostMapping("/novitrosak")
	public String procesForm(@Valid Trosak trosak, BindingResult bindingResult, Model model, HttpSession session) {

		if (bindingResult.hasErrors()) {
			logger.error("Invalid ");
			return "trosak";
		}
		logger.debug("Greske u formi za trosak");

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Novcanik novcanik = novcanikRepo.findByUsernameId(userRepo.findByName(username).getId());

		trosak.setNovcanikId(novcanik.getId());

		trosakRepo.save(trosak);

		model.addAttribute("trosak", trosak);

		novcanik.listaTroskova = (List<Trosak>) trosakRepo.findAllInNovcanik(novcanik.getId());

		Double suma = 0d;
		for (Trosak t : novcanik.listaTroskova) {
			suma -= t.getIznos();
		}
		model.addAttribute("suma", suma);
		logger.debug("Suma za novcanik: " + novcanik.getIme() + " je " + suma);

		model.addAttribute("novcanik", novcanik);

		LocalDateTime datum = trosak.getCreateDate();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		String curDate = datum.format(format);

		model.addAttribute("date", curDate);

		logger.debug("Uspjesno unesen trosak");
		return "uneseniTrosak";
	}

	@ModelAttribute("novcanik")
	public Novcanik initNovcanik(HttpSession session) {
		Long userId = userRepo.findByName(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
		Novcanik novcanik = novcanikRepo.findByUsernameId(userId);
		session.setAttribute("novcanik", novcanik);
		logger.debug("Nova sesija. Dodan novcanik:" + session.getAttribute("novcanik").toString());
		return new Novcanik();
	}

	@GetMapping("/isprazniNovcanik")
	public String resetWallet(SessionStatus status, HttpSession session) {
		trosakRepo.deleteByNovcanik(((Novcanik) session.getAttribute("novcanik")).getId());
		status.setComplete();
		return "redirect:/troskovi/novitrosak";
	}
	/*
	 * @GetMapping("/role") public String pogledajRole(Model model) { String
	 * currentUser =
	 * SecurityContextHolder.getContext().getAuthentication().getName(); List<Auth>
	 * auths = new ArrayList<>(); auths=(List<Auth>) adminRepo.findAll(); List<Auth>
	 * modelAuths = new ArrayList<>(); for (Auth a : auths) { if
	 * (!a.getUser().equals(currentUser)) { modelAuths.add(a); } }
	 * model.addAttribute("auths", modelAuths); return "roleStranica"; }
	 * 
	 * @PostMapping("/role") public String procesAuth(Auth auth) {
	 * if(auth.getRole().equals("ROLE_USER")) { adminRepo.save(new
	 * Auth(auth.getUser(), "ROLE_ADMIN")); } else {
	 * adminRepo.deleteByUserAndRole(auth.getUser(), "ROLE_ADMIN"); } return
	 * "trosak"; }
	 */
}