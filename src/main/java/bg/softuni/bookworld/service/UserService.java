package bg.softuni.bookworld.service;

import bg.softuni.bookworld.data.UserRepository;
import bg.softuni.bookworld.model.User;
import bg.softuni.bookworld.service.dto.UserProfileDTO;
import bg.softuni.bookworld.web.dto.UserLoginDTO;
import bg.softuni.bookworld.web.dto.UserRegisterDTO;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
    }

    public void register(UserRegisterDTO userRegisterDTO) {
        User user = this.modelMapper.map(userRegisterDTO, User.class);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        userRepository.save(user);
    }

    public void login(UserLoginDTO loginData) {
        User user = userRepository.findByUsername(loginData.getUsername());

        if (user == null){
            //todo throw exeption
            return;
        }

        if (passwordEncoder.matches(loginData.getPassword(), user.getPassword()) && !currentUser.isLoggedIn()){
            currentUser.setUser(user);
        }
    }

    public void logout() {
        currentUser.setUser(null);
    }

    public UserProfileDTO getProfileData(){
        return modelMapper.map(currentUser.getUser(), UserProfileDTO.class);
    }
}
