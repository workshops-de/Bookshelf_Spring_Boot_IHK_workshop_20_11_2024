package de.workshops.bookshelf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.net.URL;

@ConfigurationProperties("bookshelf")
public record BookshelfProperties(
        String title,
        String version,
        @DefaultValue("2000") int capacity,
        License license
) {}

record License(String name, URL url) {}