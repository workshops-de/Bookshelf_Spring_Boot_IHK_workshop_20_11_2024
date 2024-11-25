package de.workshops.bookshelf;

import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
//@ConfigurationPropertiesScan
public class BookshelfApplication {

	public BookshelfApplication() {
		// MDC = Mapped Diagnostic Context
		MDC.put("user", "Birgit");
	}

	public static void main(String[] args) {
		SpringApplication.run(BookshelfApplication.class, args);
	}
}
