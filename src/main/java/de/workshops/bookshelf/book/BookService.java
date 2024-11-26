package de.workshops.bookshelf.book;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookJdbcTemplateRepository bookRepository;

    public BookService(final BookJdbcTemplateRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    List<Book> getAllBooks() {
        return bookRepository.findAllBooks();
    }

    Book getBookByIsbn(String isbn) {
        return this.bookRepository.findAllBooks().stream()
                .filter(book -> hasIsbn(book, isbn))
                .findFirst()
                .orElseThrow(() -> new BookException("No Book found"));
    }

    Book getBookByAuthor(String author) {
        return this.bookRepository.findAllBooks().stream()
                .filter(book -> hasAuthor(book, author))
                .findFirst()
                .orElseThrow(() -> new BookException("No Book found"));
    }

    List<Book> searchBook(BookSearchRequest bookSearchRequest) {
        return this.bookRepository.findAllBooks().stream()
                .filter(book -> hasAuthor(book, bookSearchRequest.author()) || hasIsbn(book, bookSearchRequest.isbn()))
                .toList();
    }

    Book createBook(Book book) {
        return this.bookRepository.save(book);
    }
    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }

}
