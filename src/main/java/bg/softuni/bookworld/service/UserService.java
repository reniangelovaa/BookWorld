package bg.softuni.bookworld.service;

import bg.softuni.bookworld.data.UserRepository;
import bg.softuni.bookworld.model.User;
import bg.softuni.bookworld.service.dto.UserProfileDTO;
import bg.softuni.bookworld.web.dto.UserLoginDTO;
import bg.softuni.bookworld.web.dto.UserRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;


    public void register(UserRegisterDTO userRegisterDTO) {
        User user = this.modelMapper.map(userRegisterDTO, User.class);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        userRepository.save(user);
    }

    public UserProfileDTO getProfileData() {
        return modelMapper.map(userHelperService.getUser(), UserProfileDTO.class);
    }

    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean doPasswordsMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}


