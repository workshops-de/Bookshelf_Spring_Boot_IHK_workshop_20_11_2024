package de.workshops.bookshelf.book;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BookServiceUsingJpaRepository {
    private final BookJpaRepository bookRepository;

    BookServiceUsingJpaRepository(final BookJpaRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    Book getBookByIsbn(String isbn) {
        return this.bookRepository.findByIsbn(isbn);
    }

    Book getBookByAuthor(String author) {
        return this.bookRepository.findByAuthorContaining(author);
    }

    List<Book> searchBook(BookSearchRequest bookSearchRequest) {
        return bookRepository.searchByIsbnOrAuthorContaining(bookSearchRequest.isbn(), bookSearchRequest.author());
    }

    Book createBook(Book book) {
        return this.bookRepository.save(book);
    }
}
