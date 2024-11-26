package de.workshops.bookshelf.book;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookNamedParameterJdbcTemplateRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BookNamedParameterJdbcTemplateRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    List<Book> findAllBooks() {
        var sql = "select * from book";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class));
    }

    Book findBookByIsbn(String isbn) {
        var sql = "select * from book where isbn=:isbn";
        var mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("isbn", isbn);

        return jdbcTemplate.queryForObject(sql, mapSqlParameterSource, new BeanPropertyRowMapper<>(Book.class));
    }

    Book save(Book book) {
        var sql = "insert into book (title, author, isbn, description) values (:title, :author, :isbn, :description)";

        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(book));
        return book;
    }
}
