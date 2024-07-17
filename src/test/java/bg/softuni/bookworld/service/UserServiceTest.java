package bg.softuni.bookworld.service;

import bg.softuni.bookworld.data.RoleRepository;
import bg.softuni.bookworld.data.UserRepository;
import bg.softuni.bookworld.model.Role;
import bg.softuni.bookworld.model.User;
import bg.softuni.bookworld.model.enums.RoleType;
import bg.softuni.bookworld.web.dto.UserRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private ModelMapper mockModelMapper;

    @BeforeEach
    void setUp() {
        reset(mockUserRepository, mockRoleRepository, mockPasswordEncoder, mockModelMapper);
    }

    @Test
    void testRegister_SuccessfulRegistration() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("testUser");
        userRegisterDTO.setPassword("password123");

        Role userRole = new Role();
        userRole.setId(1L);
        userRole.setRole(RoleType.USER);

        when(mockModelMapper.map(userRegisterDTO, User.class)).thenReturn(new User());
        when(mockPasswordEncoder.encode(userRegisterDTO.getPassword())).thenReturn("hashedPassword");
        when(mockRoleRepository.findByRole(RoleType.USER)).thenReturn(Optional.of(userRole));
        when(mockUserRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });


        userService.register(userRegisterDTO);

        verify(mockModelMapper, times(1)).map(userRegisterDTO, User.class);
        verify(mockPasswordEncoder, times(1)).encode(userRegisterDTO.getPassword());
        verify(mockRoleRepository, times(1)).findByRole(RoleType.USER);
        verify(mockUserRepository, times(1)).save(any(User.class));
    }

}
