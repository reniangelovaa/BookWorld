package bg.softuni.bookworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients(basePackages = "bg.softuni.bookworld.client")
public class BookWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookWorldApplication.class, args);
	}

}
