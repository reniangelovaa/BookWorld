package bg.softuni.bookworld.service;

import bg.softuni.bookworld.data.RoleRepository;
import bg.softuni.bookworld.data.UserRepository;
import bg.softuni.bookworld.model.Role;
import bg.softuni.bookworld.model.User;
import bg.softuni.bookworld.model.enums.RoleType;
import bg.softuni.bookworld.service.dto.UserProfileDTO;
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

import static org.junit.jupiter.api.Assertions.*;
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
    @Mock
    private UserHelperService userHelperService;

    @BeforeEach
    void setUp() {
        reset(mockUserRepository, mockRoleRepository, mockPasswordEncoder, mockModelMapper);
    }

    @Test
    void testRegister() {
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

    @Test
    void testGetProfileData() {
        User user = new User();
        user.setUsername("testUser");

        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUsername("testUser");

        when(userHelperService.getUser()).thenReturn(user);
        when(mockModelMapper.map(user, UserProfileDTO.class)).thenReturn(userProfileDTO);

        UserProfileDTO result = userService.getProfileData();

        assertEquals("testUser", result.getUsername());
    }

    @Test
    void testIsUsernameTaken_TRUE() {
        when(mockUserRepository.existsByUsername("testUser")).thenReturn(true);

        boolean result = userService.isUsernameTaken("testUser");

        assertTrue(result);
    }

    @Test
    void testIsUsernameTaken_FALSE() {
        when(mockUserRepository.existsByUsername("testUser")).thenReturn(false);

        boolean result = userService.isUsernameTaken("testUser");

        assertFalse(result);
    }

    @Test
    void testIsEmailTaken_TRUE() {
        when(mockUserRepository.existsByEmail("test@example.com")).thenReturn(true);

        boolean result = userService.isEmailTaken("test@example.com");

        assertTrue(result);
    }

    @Test
    void testIsEmailTaken_FALSE() {
        when(mockUserRepository.existsByEmail("test@example.com")).thenReturn(false);

        boolean result = userService.isEmailTaken("test@example.com");

        assertFalse(result);
    }

    @Test
    void testDoPasswordsMatch_TRUE() {
        boolean result = userService.doPasswordsMatch("password123", "password123");

        assertTrue(result);
    }

    @Test
    void testDoPasswordsMatch_FALSE() {
        boolean result = userService.doPasswordsMatch("password123", "password456");

        assertFalse(result);
    }
}