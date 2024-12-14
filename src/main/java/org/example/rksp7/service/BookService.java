package org.example.rksp7.service;


import org.example.rksp7.model.Book;
import org.example.rksp7.repository.BookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Mono<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Mono<Book> createBook(Book book) {
        return bookRepository.save(book);
    }

    public Mono<Book> updateBook(Long id, Book book) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("Book not found")))
                .flatMap(existingBook -> {
                    existingBook.setTitle(book.getTitle());
                    existingBook.setAuthor(book.getAuthor());
                    existingBook.setPath(book.getPath());
                    return bookRepository.save(existingBook);
                })
                .onErrorResume(e -> Mono.error(new RuntimeException("Update failed", e)));
    }

    public Mono<Void> deleteBook(Long id) {
        return bookRepository.deleteById(id);
    }

    public Flux<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }
}
