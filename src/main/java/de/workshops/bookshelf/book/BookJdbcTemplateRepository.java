package de.workshops.bookshelf.book;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookJdbcTemplateRepository {
    private final JdbcTemplate jdbcTemplate;

    public BookJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    List<Book> findAllBooks() {
        var sql = "select * from book";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class));
    }

    Book findBookByIsbn(String isbn) {
        var sql = "select * from book where isbn=?";

        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Book.class), isbn);
    }

    Book save(Book book) {
        var sql = "insert into book (title, author, isbn, description) values (?, ?, ?, ?)";


        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getIsbn(), book.getDescription());
        return book;
    }
}
