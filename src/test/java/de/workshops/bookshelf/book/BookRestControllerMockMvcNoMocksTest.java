package de.workshops.bookshelf.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled("Tests nicht lauffähig, da ein Test-Slice verwendet wird, dieses aber die DB-Seite nicht abdeckt. Ohne Mocking kommt man hie rnicht sehr weit.")
@WebMvcTest(BookRestController.class)
@Import({BookService.class, BookJdbcTemplateRepository.class, JdbcTemplate.class})
class BookRestControllerMockMvcNoMocksTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllBooks() throws Exception {
        var mvcResult = mockMvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andReturn();

        var payload = mvcResult.getResponse().getContentAsString();
        List<Book> books = objectMapper.readValue(payload, new TypeReference<>() {
        });

        assertThat(books).hasSize(3);
    }

    @Test
    void getBookByAuthor_wrongRequest() throws Exception {
        mockMvc.perform(get("/book")
                        .param("author", "Wo"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createBook() throws Exception {
        String isbn = "1111111111";
        String title = "My first book";
        String author = "Birgit Kratz";
        String description = "A MUST read";

        mockMvc.perform(post("/book")
                        .content("""
                                {
                                    "isbn": "%s",
                                    "title": "%s",
                                    "author": "%s",
                                    "description": "%s"
                                }""".formatted(isbn, title, author, description))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @TestConfiguration
    static class ObjectMapperConfiguration {
        @Bean
        public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
            return builder -> builder.featuresToEnable(SerializationFeature.INDENT_OUTPUT);
        }
    }
}
