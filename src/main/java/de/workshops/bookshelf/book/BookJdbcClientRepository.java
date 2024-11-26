package de.workshops.bookshelf.book;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookJdbcClientRepository {

    private final JdbcClient jdbcClient;

    public BookJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    List<Book> findAllBooks() {
        var sql = "select * from book";

        return jdbcClient.sql(sql)
                .query(new BeanPropertyRowMapper<>(Book.class))
                .list();
    }

    Book findBookByIsbn(String isbn) {
        var sql = "select * from book where isbn=:isbn";

        return jdbcClient.sql(sql)
                .param("isbn", isbn)
                .query(new BeanPropertyRowMapper<>(Book.class))
                .single();
    }

    Book save(Book book) {
        var sql = "insert into book (title, author, isbn, description) values (:title, :author, :isbn, :description)";

        jdbcClient.sql(sql)
                .param("title", book.getTitle())
                .param("author", book.getAuthor())
                .param("isbn", book.getIsbn())
                .param("description", book.getDescription())
                .update();
        return book;
    }

}
