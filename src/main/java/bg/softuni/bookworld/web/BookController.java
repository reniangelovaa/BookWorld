package bg.softuni.bookworld.web;

import bg.softuni.bookworld.client.CommentService;
import bg.softuni.bookworld.client.CommentsClient;
import bg.softuni.bookworld.client.dto.CommentDTO;
import bg.softuni.bookworld.data.BookRepository;
import bg.softuni.bookworld.model.Category;
import bg.softuni.bookworld.model.enums.CategoryType;
import bg.softuni.bookworld.service.BookService;
import bg.softuni.bookworld.service.CategoryService;
import bg.softuni.bookworld.service.dto.BookDetailsDTO;
import bg.softuni.bookworld.web.dto.AddBookDTO;
import bg.softuni.bookworld.service.dto.BookShortInfoDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final CommentsClient commentsClient;
    private final CommentService commentService;


    @GetMapping("/shop")
    public String shop(Model model) {
        List<BookShortInfoDTO> books = bookService.getAll();
        model.addAttribute("allBooks", books);
        return "shop";
    }

    @GetMapping("/add-book")
    public ModelAndView addBook(){
        ModelAndView modelAndView = new ModelAndView("add-book");
        modelAndView.addObject("book", new AddBookDTO());
        modelAndView.addObject("categoryTypes", CategoryType.values());
        return modelAndView;
    }

    @ModelAttribute("bookData")
    public AddBookDTO addBookDTO(){
        return new AddBookDTO();
    }

    @PostMapping("/add-book")
    public String doAddBook(@Valid AddBookDTO data, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            return "add-book";
        }

        bookService.addBook(data);
        return "redirect:/add-book";
    }


    @GetMapping("/book/{id}")
    public ModelAndView showBookDetails(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView("book-details");
        BookDetailsDTO dto = bookService.getBookDetails(id);
        List<CommentDTO> comments = bookService.getCommentsByBookId(id);

        modelAndView.addObject("book", dto);
        modelAndView.addObject("comments", comments);
        modelAndView.addObject("commentDto", new CommentDTO());

        return modelAndView;
    }

    @GetMapping("/shop/category/{categoryId}")
    public String getBooksByCategory(@PathVariable Long categoryId, Model model) {
        Category category = categoryService.findById(categoryId);
        List<BookShortInfoDTO> books = bookService.getBooksByCategory(category);

        model.addAttribute("books", books);
        model.addAttribute("category", category);

        return "categories/" + category.getName().toString().toLowerCase();
    }

    @GetMapping("shop/author/{authorName}")
    public String getBooksByAuthor(@PathVariable String authorName, Model model){
        List<BookShortInfoDTO> booksByAuthor = bookService.getBooksByAuthor(authorName);
        model.addAttribute("books", booksByAuthor);
        model.addAttribute("authorName", authorName);
        return "books-by-author";
    }

@PostMapping("/book/{bookId}/comments")
public String addComment(@PathVariable Long bookId, @ModelAttribute CommentDTO commentDto, RedirectAttributes redirectAttributes) {
    commentDto.setBookId(bookId);
    commentService.addComment(commentDto);
    redirectAttributes.addFlashAttribute("message", "Comment added successfully!");
    return "redirect:/book/" + bookId;
}


    @DeleteMapping("/book/{bookId}/comments/{commentId}")
    public String deleteComment(
            @PathVariable long bookId,
            @PathVariable long commentId,
            RedirectAttributes redirectAttributes) {

        commentsClient.deleteComment(commentId);

        redirectAttributes.addFlashAttribute("message", "Comment deleted successfully!");
        return "redirect:/book/" + bookId;
    }

    @ModelAttribute("commentDto")
    public CommentDTO commentDto() {
        return new CommentDTO();
    }
}
