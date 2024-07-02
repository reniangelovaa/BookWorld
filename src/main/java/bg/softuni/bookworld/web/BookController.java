package bg.softuni.bookworld.web;

import bg.softuni.bookworld.service.BookService;
import bg.softuni.bookworld.service.dto.BookShortInfoDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shop")
    public String shop(Model model){
        List<BookShortInfoDTO> books = bookService.getAll();
        model.addAttribute("allBooks", books);
        return "shop";
    }
}
