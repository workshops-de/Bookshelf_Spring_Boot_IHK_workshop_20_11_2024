package de.workshops.bookshelf.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface BookJpaRepository extends JpaRepository<Book, Long> {
    Book findByIsbn(String isbn);

    Book findByAuthorContaining(String author);

    List<Book> searchByIsbnOrAuthorContaining(String isbn, String author);

    @Query("select book from Book book where book.isbn=?1 or book.author like ?2")
    List<Book> searchBySearchRequest(String isbn, String author);
}
