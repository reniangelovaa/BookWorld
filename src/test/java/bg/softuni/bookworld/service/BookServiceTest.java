package bg.softuni.bookworld.service;

import bg.softuni.bookworld.data.BookRepository;
import bg.softuni.bookworld.model.Author;
import bg.softuni.bookworld.model.Book;
import bg.softuni.bookworld.model.Category;
import bg.softuni.bookworld.model.Picture;
import bg.softuni.bookworld.service.dto.BookDetailsDTO;
import bg.softuni.bookworld.service.dto.BookShortInfoDTO;
import bg.softuni.bookworld.service.exeption.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository mockBookRepository;

    @Mock
    private ModelMapper mockModelMapper;

    @InjectMocks
    private BookService bookService;

    @Mock
    private Random random;

    @BeforeEach
    void setUp() {
        reset(mockBookRepository, mockModelMapper);
    }

    private Author getAuthor(){
        Author author = new Author();
        author.setFullName("John Doe");
        return author;
    }

    private Picture getPicture(){
        Picture picture = new Picture();
        picture.setUrl("http://example.com/image.jpg");
        return picture;
    }

    private BookShortInfoDTO getBookShortInfoDTO(Long id){
        BookShortInfoDTO dto = new BookShortInfoDTO();
        dto.setId(id);
        dto.setName("Test Title " + id);
        dto.setImageUrl("http://example.com/picture" + id + ".jpg");
        dto.setAuthor("Test Author");
        return dto;
    }


    @Test
    void testGetAll() {

        Book book1 = new Book();
        book1.setId(1L);
        book1.setAuthor(getAuthor());
        book1.setPictures(Set.of(getPicture()));
        Book book2 = new Book();
        book2.setId(2L);
        book2.setAuthor(getAuthor());
        book2.setPictures(Set.of(getPicture()));

        List<Book> books = List.of(book1, book2);

        BookShortInfoDTO dto1 = getBookShortInfoDTO(1L);
        BookShortInfoDTO dto2 = getBookShortInfoDTO(2L);

        when(mockBookRepository.findAll()).thenReturn(books);
        when(mockModelMapper.map(book1, BookShortInfoDTO.class)).thenReturn(dto1);
        when(mockModelMapper.map(book2, BookShortInfoDTO.class)).thenReturn(dto2);

        List<BookShortInfoDTO> result = bookService.getAll();

        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
    }

    @Test
    void testGetRandomBook() {

        Book book = new Book();
        book.setId(1L);
        book.setAuthor(getAuthor());
        book.setPictures(Set.of(getPicture()));

        BookShortInfoDTO dto = getBookShortInfoDTO(1L);

        when(mockBookRepository.count()).thenReturn(1L);
        when(mockBookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(mockModelMapper.map(book, BookShortInfoDTO.class)).thenReturn(dto);
        lenient().when(random.nextLong(anyLong())).thenReturn(0L);

        BookShortInfoDTO result = bookService.getRandomBook();

        assertEquals("John Doe", result.getAuthor());
        assertEquals("http://example.com/image.jpg", result.getImageUrl());
    }

    @Test
    void testGetThreeUniqueRandomBooks() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setAuthor(getAuthor());
        book1.setPictures(Set.of(getPicture()));
        Book book2 = new Book();
        book2.setId(2L);
        book2.setAuthor(getAuthor());
        book2.setPictures(Set.of(getPicture()));
        Book book3 = new Book();
        book3.setId(3L);
        book3.setAuthor(getAuthor());
        book3.setPictures(Set.of(getPicture()));

        BookShortInfoDTO dto1 = getBookShortInfoDTO(1L);
        BookShortInfoDTO dto2 = getBookShortInfoDTO(2L);
        BookShortInfoDTO dto3 = getBookShortInfoDTO(3L);

        when(mockBookRepository.count()).thenReturn(3L);
        when(mockBookRepository.findById(1L)).thenReturn(Optional.of(book1));
        when(mockBookRepository.findById(2L)).thenReturn(Optional.of(book2));
        when(mockBookRepository.findById(3L)).thenReturn(Optional.of(book3));
        when(mockModelMapper.map(book1, BookShortInfoDTO.class)).thenReturn(dto1);
        when(mockModelMapper.map(book2, BookShortInfoDTO.class)).thenReturn(dto2);
        when(mockModelMapper.map(book3, BookShortInfoDTO.class)).thenReturn(dto3);


        lenient().when(random.nextLong(3L)).thenReturn(0L, 1L, 2L);

        List<BookShortInfoDTO> result = bookService.getThreeUniqueRandomBooks();

        assertEquals(Set.of(dto1, dto2, dto3), Set.copyOf(result));
    }


    @Test
    void testGetBookDetails() {
        Book book = new Book();
        book.setId(1L);
        book.setAuthor(getAuthor());
        book.setPictures(Set.of(getPicture()));
        BookDetailsDTO dto = new BookDetailsDTO();

        when(mockBookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(mockModelMapper.map(book, BookDetailsDTO.class)).thenReturn(dto);

        BookDetailsDTO result = bookService.getBookDetails(1L);

        assertEquals(dto, result);
    }

    @Test
    void testGetBookDetailsNotFound() {
        when(mockBookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> {
            bookService.getBookDetails(1L);
        });
    }


    @Test
    void testGetBooksByCategory() {
        Category category = new Category();
        Book book1 = new Book();
        book1.setId(1L);
        book1.setAuthor(getAuthor());
        book1.setPictures(Set.of(getPicture()));
        Book book2 = new Book();
        book2.setId(2L);
        book2.setAuthor(getAuthor());
        book2.setPictures(Set.of(getPicture()));
        BookShortInfoDTO dto1 = getBookShortInfoDTO(1L);
        BookShortInfoDTO dto2 = getBookShortInfoDTO(2L);
        when(mockBookRepository.findAllByCategory(category)).thenReturn(List.of(book1, book2));
        when(mockModelMapper.map(book1, BookShortInfoDTO.class)).thenReturn(dto1);
        when(mockModelMapper.map(book2, BookShortInfoDTO.class)).thenReturn(dto2);

        List<BookShortInfoDTO> result = bookService.getBooksByCategory(category);

        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
    }
}
