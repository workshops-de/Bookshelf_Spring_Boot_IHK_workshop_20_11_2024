package de.workshops.bookshelf.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BookRestControllerMockMvcTest {

    @LocalServerPort
    int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Captor
    ArgumentCaptor<String> isbnCaptor;

    @Test
    void getAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(new Book()));

        var mvcResult = mockMvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andReturn();

        var payload = mvcResult.getResponse().getContentAsString();
        List<Book> books = objectMapper.readValue(payload, new TypeReference<>() {
        });

        assertThat(books).hasSize(1);

        verify(bookService).getAllBooks();
        System.out.println("Random Port: " +port);
    }

    @Test
    void getBookByIsbn_ok() throws Exception {
        when(bookService.getBookByIsbn(isbnCaptor.capture())).thenReturn(new Book());

        mockMvc.perform(get("/book/1234567890"))
                .andExpect(status().isOk());

        var isbn = isbnCaptor.getValue();
        assertThat(isbn).isEqualTo("1234567890");
    }

    @Test
    void getBookByIsbn_throwsException() throws Exception {
        doThrow(BookException.class).when(bookService).getBookByIsbn(anyString());

        mockMvc.perform(get("/book/1234567890"))
                .andExpect(status().isIAmATeapot());
    }

    @Test
    void getBookByAuthor_wrongRequest() throws Exception {
        mockMvc.perform(get("/book")
                        .param("author", "Wo"))
                .andExpect(status().isBadRequest());
    }
}
