package bg.softuni.bookworld.web;

import bg.softuni.bookworld.service.UserService;
import bg.softuni.bookworld.web.dto.UserLoginDTO;
import bg.softuni.bookworld.web.dto.UserRegisterDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users/register")
    public String viewRegister(Model model) {
        model.addAttribute("registerData", new UserRegisterDTO());
        return "register";
    }

    @PostMapping("users/register")
    public String doRegister(@Valid UserRegisterDTO data, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()){
//            redirectAttributes.addFlashAttribute("registerData", data);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.UserRegisterDTO", bindingResult);
//            return "redirect:/users/register";
//        }

        userService.register(data);
        return "redirect:/users/login";
    }

    @GetMapping("/users/login")
    public ModelAndView viewLogin() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("loginData", new UserLoginDTO());

        return modelAndView;
    }


    @GetMapping("users/login-error")
    public ModelAndView viewLoginError() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("showErrorMessage", true);
        modelAndView.addObject("loginData", new UserLoginDTO());
        return modelAndView;
    }


    @GetMapping("users/profile")
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("profileData", userService.getProfileData());
        return modelAndView;
    }
}
