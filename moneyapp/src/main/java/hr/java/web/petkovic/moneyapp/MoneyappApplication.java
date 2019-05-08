package hr.java.web.petkovic.moneyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("hr.java.web.petkovic.moneyapp.repository")
@SpringBootApplication()
public class MoneyappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyappApplication.class, args);
	}

}
