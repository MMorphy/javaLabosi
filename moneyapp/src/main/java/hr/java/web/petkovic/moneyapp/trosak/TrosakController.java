package hr.java.web.petkovic.moneyapp.trosak;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/troskovi")
@SessionAttributes({ "vrste", "novcanik" }) // ovo si mijenjao
public class TrosakController {
	private static Logger logger = LoggerFactory.getLogger(TrosakController.class);

	@GetMapping("/novitrosak")
	public String trosakForm(Model model, HttpSession session) {
		model.addAttribute("trosak", new Trosak());
		model.addAttribute("vrste", Trosak.VrstaTroskaEnum.values());
		logger.debug("Poziv get-a s putanjom: /troskovi/novitrosak");
		return "trosak";
	}

	@PostMapping("/novitrosak")
	public String processForm(@Valid Trosak trosak, BindingResult bindingResult, Model model, HttpSession session) {
		logger.debug("Poziv posta-a s putanjom: /troskovi/novitrosak");
		if (bindingResult.hasErrors()) {
			logger.error("Greške u formi. Broj greški: " + bindingResult.getErrorCount());
			return "trosak";
		}
		logger.info("Unesen je novi trosak: " + trosak.toString());
		((Novcanik) session.getAttribute("novcanik")).addTrosak(trosak);
		model.addAttribute("trosak", trosak);
		model.addAttribute("suma", ((Novcanik) session.getAttribute("novcanik")).getTroskoviSum());

		Date date = new Date(System.currentTimeMillis());
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
		model.addAttribute("date", df.format(date));

		logger.debug("Trenutni model:" + model.toString());
		return "uneseniTrosak";
	}

	@GetMapping("/isprazniNovcanik")
	public String resetWallet(SessionStatus status) {
		logger.info("Praznjenje novcanika. Kraj sessije: " + status.hashCode());
		status.setComplete();
		return "redirect:/troskovi/novitrosak";
	}

	// ovo si mijenjao
	@ModelAttribute("novcanik")
	public Novcanik setNovcanik(HttpSession session) {
		Novcanik novcanik = new Novcanik("Default", Novcanik.VrstaNovcanikaEnum.GOTOVINA);
		session.setAttribute("novcanik", novcanik);
		logger.info("Pocetak sessije. Napravljen novi novcanik.");
		return novcanik;
	}


}