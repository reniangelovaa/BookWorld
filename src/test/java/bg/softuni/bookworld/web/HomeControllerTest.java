package bg.softuni.bookworld.web;

import bg.softuni.bookworld.service.BookService;
import bg.softuni.bookworld.service.dto.BookShortInfoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HomeControllerTest {

    @Mock
    private BookService bookService;
    @InjectMocks
    private HomeController homeController;

    @Test
    public void testIndex() {
        MockitoAnnotations.openMocks(this);

        List<BookShortInfoDTO> uniqueBooks = Arrays.asList(
                new BookShortInfoDTO(),
                new BookShortInfoDTO(),
                new BookShortInfoDTO()
        );

        when(bookService.getThreeUniqueRandomBooks()).thenReturn(uniqueBooks);

        Model model = new BindingAwareModelMap();
        String viewName = homeController.index(model);

        assertEquals("index", viewName);
        assertEquals(uniqueBooks, model.getAttribute("uniqueBooks"));
    }

    @Test
    public void testAbout() {
        MockitoAnnotations.openMocks(this);

        ModelAndView modelAndView = homeController.about();

        assertEquals("about-us", modelAndView.getViewName());
    }

}
