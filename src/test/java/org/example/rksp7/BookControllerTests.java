package org.example.rksp7;



import org.example.rksp7.model.Book;
import org.example.rksp7.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class BookControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookService bookService;


    @Test
    public void testGetBookById() {
        Book book = new Book();
        book.setId(1L);

        Mockito.when(bookService.getBookById(1L)).thenReturn(Mono.just(book));

        webTestClient.get()
                .uri("/book/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .isEqualTo(book);
    }

    @Test
    public void testGetAllBooks() {
        Book book1 = new Book();
        book1.setId(1L);

        Book book2 = new Book();
        book2.setId(2L);

        Mockito.when(bookService.getAllBooks()).thenReturn(Flux.just(book1, book2));

        webTestClient.get()
                .uri("/book")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class)
                .hasSize(2)
                .contains(book1, book2);
    }

    @Test
    public void testStreamBooks() {
        Book book1 = new Book();
        book1.setId(1L);

        Mockito.when(bookService.getAllBooks()).thenReturn(Flux.just(book1));

        webTestClient.get()
                .uri("/book/stream")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class)
                .hasSize(1)
                .contains(book1);
    }

    @Test
    public void testCreateBook() {
        Book book = new Book();
        book.setId(1L);

        Mockito.when(bookService.createBook(Mockito.any(Book.class))).thenReturn(Mono.just(book));

        webTestClient.post()
                .uri("/book")
                .bodyValue(book)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .isEqualTo(book);
    }

    @Test
    public void testUpdateBook() {
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Old Title");

        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Updated Title");

        Mockito.when(bookService.updateBook(1L, updatedBook)).thenReturn(Mono.just(updatedBook));

        webTestClient.put()
                .uri("/book/1")
                .bodyValue(updatedBook)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .isEqualTo(updatedBook);
    }

    @Test
    public void testDeleteBook() {
        Mockito.when(bookService.deleteBook(1L)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/book/1")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testGetBooksByAuthor() {
        Book book1 = new Book();
        book1.setId(1L);

        Book book2 = new Book();
        book2.setId(2L);

        // Мокируем вызов получения постов по автору
        Mockito.when(bookService.getBooksByAuthor("Author 1")).thenReturn(Flux.just(book1, book2));

        webTestClient.get()
                .uri("/book/author/Author 1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class)
                .hasSize(2)
                .contains(book1, book2);
    }
}
