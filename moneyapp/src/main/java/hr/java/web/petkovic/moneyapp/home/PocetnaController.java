package hr.java.web.petkovic.moneyapp.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PocetnaController 
{

	private static Logger logger = LoggerFactory.getLogger(PocetnaController.class);
	@GetMapping("/")
	public String showHome() 
	{
		logger.info("Poziv index-a.");
		return "index";
	}

}
