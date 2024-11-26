package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BookJdbcTemplateRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookJdbcTemplateRepositoryTest {

    @Autowired
    private BookJdbcTemplateRepository repository;

    @Test
    void findAllBooks() {
        var allBooks = repository.findAllBooks();

        assertThat(allBooks).hasSize(3);
    }

}