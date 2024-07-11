package bg.softuni.bookworld.service;

import bg.softuni.bookworld.data.BookRepository;
import bg.softuni.bookworld.model.Book;
import bg.softuni.bookworld.model.Picture;
import bg.softuni.bookworld.service.dto.BookShortInfoDTO;
import bg.softuni.bookworld.web.dto.AddBookDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

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

//    @Transactional
//    public List<BookShortInfoDTO> getByCategory(String category){
//        return bookRepository.findAllByCategory(category)
//                .stream()
//                .map(this::mapToShortInfo)
//                .toList();
//    }

    @Transactional
    public BookShortInfoDTO getRandomBook() {
        long booksCount = bookRepository.count();
        long randomId = random.nextLong(booksCount) + 1;

        Optional<Book> book = bookRepository.findById(randomId);

        return mapToShortInfo(book.get());
    }
    @Transactional
    public List<BookShortInfoDTO> getThreeUniqueRandomBooks() {
        Set<Long> selectedBookIds = new HashSet<>();
        List<BookShortInfoDTO> uniqueBooks = new ArrayList<>();

        while (uniqueBooks.size() < 3) {
            BookShortInfoDTO randomBook = getRandomBook();
            if (!selectedBookIds.contains(randomBook.getId())) {
                selectedBookIds.add(randomBook.getId());
                uniqueBooks.add(randomBook);
            }
        }

        return uniqueBooks;
    }


    private BookShortInfoDTO mapToShortInfo(Book book) {
        BookShortInfoDTO dto = modelMapper.map(book, BookShortInfoDTO.class);
        Optional<Picture> first = book.getPictures().stream().findAny();
        String authorName = book.getAuthor().getFullName();
        dto.setImageUrl(first.get().getUrl());
        dto.setAuthor(authorName);

        return dto;
    }


    public boolean add(AddBookDTO data) {
        Book toInsert = modelMapper.map(data, Book.class);

        return false;
    }
}
