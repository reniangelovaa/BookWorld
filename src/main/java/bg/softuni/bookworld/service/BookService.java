package bg.softuni.bookworld.service;

import bg.softuni.bookworld.client.CommentsClient;
import bg.softuni.bookworld.client.dto.CommentDTO;
import bg.softuni.bookworld.data.AuthorRepository;
import bg.softuni.bookworld.data.BookRepository;
import bg.softuni.bookworld.data.CategoryRepository;
import bg.softuni.bookworld.data.PictureRepository;
import bg.softuni.bookworld.model.*;
import bg.softuni.bookworld.model.enums.CategoryType;
import bg.softuni.bookworld.service.dto.BookDetailsDTO;
import bg.softuni.bookworld.service.dto.BookShortInfoDTO;
import bg.softuni.bookworld.service.exeption.ObjectNotFoundException;
import bg.softuni.bookworld.web.dto.AddBookDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final Random random = new Random();
    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;
    private final PictureRepository pictureRepository;
    private final CommentsClient commentsClient;
    private final CategoryRepository categoryRepository;

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
    @Transactional
    public BookDetailsDTO getBookDetails(Long id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book book = optionalBook.orElseThrow(() -> new ObjectNotFoundException("Book", id.toString()));
        BookDetailsDTO dto = modelMapper.map(book, BookDetailsDTO.class);
        Optional<Picture> picture = book.getPictures().stream().findAny();
        String authorName = book.getAuthor().getFullName();
        List<String> categories = book.getCategories().stream()
                .map(category -> category.getName().name())
                .collect(Collectors.toList());
        dto.setImageUrl(picture.get().getUrl());
        dto.setAuthor(authorName);
        dto.setCategory(categories);

        return dto;

    }

    @Transactional
    public void addBook(AddBookDTO data) {
        Book book = modelMapper.map(data, Book.class);
        Author author = authorRepository.findByFullName(data.getAuthor());
        if (author == null) {
            author = new Author();
            author.setFullName(data.getAuthor());
            authorRepository.save(author);
        }
        book.setAuthor(author);

        Set<Category> categories = new HashSet<>();
        for (String categoryName : data.getCategories()) {
            CategoryType categoryType;
            try {
                categoryType = CategoryType.valueOf(categoryName.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid category type: " + categoryName);
            }

            Optional<Category> categoryOpt = categoryRepository.findByName(categoryType);
            Category category;
            if (categoryOpt.isPresent()) {
                category = categoryOpt.get();
            } else {
                Category newCategory = new Category();
                newCategory.setName(categoryType);
                category = categoryRepository.save(newCategory);
            }
            categories.add(category);
        }
        book.setCategories(categories);

        if (data.getImageUrl() != null && !data.getImageUrl().isEmpty()) {
            Picture picture = new Picture();
            picture.setUrl(data.getImageUrl());
            picture.setBook(book);
            Set<Picture> pictures = new HashSet<>();
            pictures.add(picture);
            book.setPictures(pictures);


            pictureRepository.save(picture);
        }

        bookRepository.save(book);

    }

    @Transactional
    public List<BookShortInfoDTO> getBooksByCategory(Category category) {
        return bookRepository.findAllByCategory(category)
                .stream()
                .map(this::mapToShortInfo)
                .toList();
    }

    public List<CommentDTO> getCommentsByBookId(Long bookId) {
        return commentsClient.getCommentsByBookId(bookId);
    }
}

