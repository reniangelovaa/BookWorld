package bg.softuni.bookworld.service;

import bg.softuni.bookworld.data.BookRepository;
import bg.softuni.bookworld.model.Book;
import bg.softuni.bookworld.model.Picture;
import bg.softuni.bookworld.service.dto.BookShortInfoDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BookService {

    private BookRepository bookRepository;
    private Random random;

    private ModelMapper modelMapper;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.modelMapper = new ModelMapper();
        this.random = new Random();
    }

    @Transactional
    public List<BookShortInfoDTO> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapToShortInfo)
                .toList();

    }

    @Transactional
    public BookShortInfoDTO getRandomBook() {
        long booksCount = bookRepository.count();
        long randomId = random.nextLong(booksCount) + 1;

        Optional<Book> book = bookRepository.findById(randomId);

        return mapToShortInfo(book.get());
    }


    private BookShortInfoDTO mapToShortInfo(Book book) {
        BookShortInfoDTO dto = modelMapper.map(book, BookShortInfoDTO.class);
        Optional<Picture> first = book.getPictures().stream().findAny();
        String authorName = book.getAuthor().getFullName();
        dto.setImageUrl(first.get().getUrl());
        dto.setAuthor(authorName);

        return dto;
    }
}
