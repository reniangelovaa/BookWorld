package bg.softuni.bookworld.web;

import bg.softuni.bookworld.service.BookService;
import bg.softuni.bookworld.service.dto.BookShortInfoDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String index(Model model){
        BookShortInfoDTO randomBook = bookService.getRandomBook();
        BookShortInfoDTO randomSecondBook = bookService.getRandomBook();
        BookShortInfoDTO randomThirdBook = bookService.getRandomBook();

        model.addAttribute("book", randomBook);
        model.addAttribute("secondBook", randomSecondBook);
        model.addAttribute("thirdBook", randomThirdBook);
        return "index";
    }
}
