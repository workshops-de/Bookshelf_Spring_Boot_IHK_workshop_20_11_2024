package de.workshops.bookshelf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class SwaggerConfiguration {

    @Bean
    public OpenAPI api(BookshelfProperties properties) {
        return new OpenAPI()
                .info(
                        new Info()
                                .title(properties.title())
                                .version(properties.version())
                                .description("This bookshelf has a capacity of %d books.".formatted(properties.capacity()))
                                .license(new License()
                                        .name(properties.license().name())
                                        .url(properties.license().url().toString())
                                )
                );
    }
}
