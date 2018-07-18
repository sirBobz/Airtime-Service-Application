package world.jumo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "world.jumo")
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@ImportResource("JdbcPoller.xml")
public class AirtimeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirtimeServiceApplication.class, args);
	}
}
