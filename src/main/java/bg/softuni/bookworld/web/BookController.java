package bg.softuni.bookworld.web;

import bg.softuni.bookworld.model.enums.CategoryType;
import bg.softuni.bookworld.service.BookService;
import bg.softuni.bookworld.web.dto.AddBookDTO;
import bg.softuni.bookworld.service.dto.BookShortInfoDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @GetMapping("/shop")
    public String shop(Model model) {
        List<BookShortInfoDTO> books = bookService.getAll();
        model.addAttribute("allBooks", books);
        return "shop";
    }

//    @GetMapping("/shop/fantasy")
//    public String fantasy(Model model){
//        List<BookShortInfoDTO> books = bookService.getByCategory("FANTASY");
//        return "fantasy";
//    }

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
    public String doAddBook(@Valid AddBookDTO data,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){

        bookService.add(data);
        return "redirect:/add-book";
    }



}
