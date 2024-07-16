package bg.softuni.bookworld.web;

import bg.softuni.bookworld.service.UserService;
import bg.softuni.bookworld.web.dto.UserLoginDTO;
import bg.softuni.bookworld.web.dto.UserRegisterDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("users/register")
    public String viewRegister(Model model) {
        model.addAttribute("registerData", new UserRegisterDTO());
        return "register";
    }

    @PostMapping("users/register")
    public String doRegister(@Valid @ModelAttribute("registerData") UserRegisterDTO data,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (userService.isUsernameTaken(data.getUsername())) {
            bindingResult.rejectValue("username", "error.username", "Username is already taken.");
        }

        if (userService.isEmailTaken(data.getEmail())) {
            bindingResult.rejectValue("email", "error.email", "Email is already taken.");
        }

        if (!userService.doPasswordsMatch(data.getPassword(), data.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Passwords do not match.");
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("registerData", data);
            return "register";
        }

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
