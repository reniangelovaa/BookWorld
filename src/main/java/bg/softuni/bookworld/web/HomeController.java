package bg.softuni.bookworld.web;

import bg.softuni.bookworld.service.BookService;
import bg.softuni.bookworld.service.dto.BookShortInfoDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    private BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<BookShortInfoDTO> uniqueRandomBooks = bookService.getThreeUniqueRandomBooks();
        model.addAttribute("uniqueBooks", uniqueRandomBooks);
        return "index";
    }

    @GetMapping("/about-us")
    public ModelAndView about() {
        return new ModelAndView("about-us");
    }

}
