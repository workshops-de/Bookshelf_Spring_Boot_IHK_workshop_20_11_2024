package de.workshops.bookshelf;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BookshelfProperties.class)
public class BookshelfConfiguration {
}
