package hr.java.web.petkovic.moneyapp.trosak;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.mapping.Array;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import hr.java.web.petkovic.moneyapp.repository.NovcanikRepository;
import hr.java.web.petkovic.moneyapp.repository.TrosakRepository;
import hr.java.web.petkovic.moneyapp.repository.UserRepository;

@Controller
@RequestMapping("/troskovi")
@SessionAttributes({ "vrste", "novcanik" })
public class TrosakController {
	private static Logger logger = LoggerFactory.getLogger(TrosakController.class);

	private TrosakRepository trosakRepo;
	private NovcanikRepository novcanikRepo;
	private UserRepository userRepo;

	public TrosakController(TrosakRepository trosakRepo, NovcanikRepository novcanikRepo, UserRepository userRepo) 
	{
		this.trosakRepo = trosakRepo;
		this.novcanikRepo = novcanikRepo;
		this.userRepo = userRepo;
	}
	@GetMapping("/novitrosak")
	public String trosakForm(Model model) {

		model.addAttribute("trosak", new Trosak());
		logger.debug("Novi trosak u modelu");

		model.addAttribute("vrste", Trosak.VrstaTroska.values());

		List<Novcanik> novcani = (List<Novcanik>) novcanikRepo.findByUser(
				userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
		List<Trosak> troskoviPoNov = new ArrayList<>();
		Map<Long, Double> mapaSuma = new HashMap<>();
		for (Novcanik nov : novcani)
		{
			int i = 0;
			troskoviPoNov = (List<Trosak>) trosakRepo.findByNovcanikId(nov.getId());
			Double suma = 0d;
			for (Trosak tr : troskoviPoNov)
			{
				suma-=tr.getIznos();
			}
			mapaSuma.put(nov.getId(), suma);
		}
		model.addAttribute("novcanici", novcani);
		model.addAttribute("mapa", mapaSuma);
		logger.info("Novcanici: " + novcanikRepo.findByUser(
				userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())));
		logger.debug("Vrste troska u modelu");

		return "trosak";
	}

	@PostMapping("/novitrosak")
	public String procesForm(@Valid Trosak trosak, BindingResult bindingResult, Model model, HttpSession session) {

		if (bindingResult.hasErrors()) {
			logger.error("Invalid ");
			return "trosak";
		}
		logger.info("trosak novcanikId" + trosak.getNovcanikId());

		Novcanik novcanik = novcanikRepo.findById(trosak.getNovcanikId()).get();

		trosakRepo.save(trosak);

		model.addAttribute("trosak", trosak);

		novcanik.listaTroskova = (List<Trosak>) trosakRepo.findByNovcanikId(novcanik.getId());

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
		User user = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		List<Novcanik> novcanik = novcanikRepo.findByUser(user);
		session.setAttribute("novcanik", novcanik);
		logger.debug("Nova sesija. Dodan novcanik:" + session.getAttribute("novcanik").toString());
		return new Novcanik();
	}

	@Transactional
	@GetMapping("/isprazniNovcanik")
	public String resetWallet(SessionStatus status, HttpSession session) {
		trosakRepo.deleteByNovcanikId(((List<Novcanik>) session.getAttribute("novcanik")).get(0).getId());
		status.setComplete();
		return "redirect:/troskovi/novitrosak";
	}

	@GetMapping("/novinovcanik")
	public String getNewWalletForm(Model model) {
		Novcanik novc = new Novcanik();
		model.addAttribute("transportnov", novc);
		model.addAttribute("vrstenov", Novcanik.TipNovcanika.values());
		return "newwallet";
	}

	@PostMapping("/novinovcanik")
	public String processWalletForm(Novcanik novcanik) {
		novcanik.setUser(userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
		novcanikRepo.save(novcanik);
		return "redirect:/troskovi/novitrosak";
	}

	@GetMapping("trazitrosak")
	public String traziTrosak()
	{
		return "traziTrosak";
	}

	@PostMapping("trazitrosak")
	public String nadjenTrosak(@RequestParam String imeTroska, Model model)
	{
		List<Trosak> troskovi = new ArrayList<>();
		User user = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		List<Novcanik> novcan = novcanikRepo.findByUser(user);
		List<Long> ids = new ArrayList<>();
		for (Novcanik n : novcan)
		{
			ids.add(n.getId());
		}
		troskovi = trosakRepo.findByNazivLikeAndNovcanikIdIn("%"+imeTroska+"%", ids);
		logger.info("troskovi" + troskovi.toString());
		model.addAttribute("listaTrazenihTroskova", troskovi);
		return "traziTrosak";
	}

	@GetMapping("nadjiNovcanik")
	public String nadjiNovcanik()
	{
		return "nadjiNovcanik";
	}

//	@PostMapping("nadjiNovcanik")
//	public String searchNovcanik(@RequestParam String korisnicko, @RequestParam String operator, @RequestParam String iznos, @RequestParam String iznos2, Model model)
//	{
//		logger.info("korisnicko" + korisnicko);
//		logger.info("iznos1:" + iznos);
//		logger.info("iznos2:" + iznos2);
//		List<Novcanik> novcanici = new ArrayList<>();
//		if(!korisnicko.isEmpty())
//		{
//			novcanici = novcanikRepo.findAllByUser_Username(korisnicko);
//		}
//		else if (!iznos.isEmpty())
//		{
//			logger.info("usao u iznos");
//			if (operator.equals(">"))
//			{
//				novcanici = novcanikRepo.findAllByListaTroskova_IznosGreaterThanEqual(Double.parseDouble(iznos));
//			}
//			else if(operator.equals("<"))
//			{
//				novcanici = novcanikRepo.findAllByListaTroskova_IznosLessThanEqual(Double.parseDouble(iznos));
//			}
//			else
//			{
//				novcanici = novcanikRepo.findAllByListaTroskova_IznosGreaterThanEqualAndListaTroskova_IznosLessThanEqual(Double.parseDouble(iznos), Double.parseDouble(iznos2));
//			}
//		}
//		if (operator.equals(">"))
//		{
//			novcanici = novcanikRepo.findAllByUser_UsernameAndListaTroskova_IznosGreaterThanEqual(korisnicko, Double.parseDouble(iznos));
//		}
//		else if (operator.equals("<"))
//		{
//			novcanici = novcanikRepo.findAllByUser_UsernameAndListaTroskova_IznosLessThanEqual(korisnicko, Double.parseDouble(iznos));
//		}
//		else
//		{
//			novcanici = novcanikRepo.findAllByUser_UsernameAndListaTroskova_IznosGreaterThanEqualAndListaTroskova_IznosLessThanEqual(korisnicko, Double.parseDouble(iznos), Double.parseDouble(iznos2));
//		}
//		model.addAttribute("listaNovcanika", novcanici);
//		return "nadjiNovcanik";
//	}
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