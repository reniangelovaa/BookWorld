package bg.softuni.bookworld.web;

import bg.softuni.bookworld.client.dto.CommentDTO;
import bg.softuni.bookworld.model.Category;
import bg.softuni.bookworld.model.enums.CategoryType;
import bg.softuni.bookworld.service.BookService;
import bg.softuni.bookworld.service.CategoryService;
import bg.softuni.bookworld.service.dto.BookDetailsDTO;
import bg.softuni.bookworld.service.dto.BookShortInfoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testShop() throws Exception {
        List<BookShortInfoDTO> books = Collections.singletonList(new BookShortInfoDTO());
        when(bookService.getAll()).thenReturn(books);

        mockMvc.perform(get("/shop"))
                .andExpect(status().isOk())
                .andExpect(view().name("shop"))
                .andExpect(model().attributeExists("allBooks"))
                .andExpect(model().attribute("allBooks", books));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testShowBookDetails() throws Exception {
        BookDetailsDTO bookDetailsDTO = new BookDetailsDTO();
        List<CommentDTO> comments = Collections.singletonList(new CommentDTO());
        when(bookService.getBookDetails(1L)).thenReturn(bookDetailsDTO);
        when(bookService.getCommentsByBookId(1L)).thenReturn(comments);

        mockMvc.perform(get("/book/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("book-details"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("comments"))
                .andExpect(model().attributeExists("commentDto"))
                .andExpect(model().attribute("book", bookDetailsDTO))
                .andExpect(model().attribute("comments", comments));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testGetBooksByCategory() throws Exception {
        Category category = new Category();
        category.setName(CategoryType.FANTASY);
        List<BookShortInfoDTO> books = Collections.singletonList(new BookShortInfoDTO());
        when(categoryService.findById(1L)).thenReturn(category);
        when(bookService.getBooksByCategory(category)).thenReturn(books);

        mockMvc.perform(get("/shop/category/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("categories/" + CategoryType.FANTASY.name().toLowerCase()))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attributeExists("category"))
                .andExpect(model().attribute("books", books))
                .andExpect(model().attribute("category", category));
    }

}
