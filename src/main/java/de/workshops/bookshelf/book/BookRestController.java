package de.workshops.bookshelf.book;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
@Validated
public class BookRestController {
    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("{isbn}")
    Book getBookByIsbn(@PathVariable @Size(min=10, max=14) String isbn) {
        return bookService.getBookByIsbn(isbn);
    }

    @GetMapping(params="author")
    Book getBookByAuthor(@RequestParam("author") @Size(min=3) String author) {
        return bookService.getBookByAuthor(author);
    }

    @PostMapping("search")
    ResponseEntity<List<Book>> searchBook(@RequestBody @Valid BookSearchRequest bookSearchRequest) {
        var foundBooks = bookService.searchBook(bookSearchRequest);

        if (foundBooks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(foundBooks);
    }

    @ExceptionHandler(BookException.class)
    ResponseEntity<String> handleBookException(BookException bookException) {
        return new ResponseEntity<>(bookException.getMessage(), HttpStatus.I_AM_A_TEAPOT);
    }
}
