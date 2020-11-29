package tech.kztar.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EntityScan("tech.kztar.persistence.model")
@EnableJpaRepositories("tech.kztar.persistence.repo")
@ComponentScan({ "tech.kztar.scheduling", "tech.kztar.dao" })
public class LoanJobsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanJobsApplication.class, args);
	}

}
