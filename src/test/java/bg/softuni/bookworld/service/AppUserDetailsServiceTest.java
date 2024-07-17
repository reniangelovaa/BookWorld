package bg.softuni.bookworld.service;

import bg.softuni.bookworld.data.UserRepository;
import bg.softuni.bookworld.model.Role;
import bg.softuni.bookworld.model.User;
import bg.softuni.bookworld.model.enums.RoleType;
import bg.softuni.bookworld.service.session.AppUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserDetailsServiceTest {

    private static final String TEST_USERNAME = "testUser";
    private static final String NOT_EXISTENT_USERNAME = "noUser";

    private AppUserDetailsService toTest;
    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp(){
        toTest = new AppUserDetailsService(mockUserRepository);
    }

    @Test
    void testLoadUserByUsername_UserFound(){
        Role adminRole = new Role();
        adminRole.setRole(RoleType.ADMINISTRATOR);

        Role userRole = new Role();
        userRole.setRole(RoleType.USER);

        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        roles.add(userRole);

        User testUser = new User();
        testUser.setUsername(TEST_USERNAME);
        testUser.setPassword("qwerty123");
        testUser.setFullName("John Doe");
        testUser.setEmail("test@example.bg");
        testUser.setRoles(roles);


        when(mockUserRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(testUser));

        UserDetails userDetails = toTest.loadUserByUsername(TEST_USERNAME);

        assertEquals(TEST_USERNAME, userDetails.getUsername());
        assertEquals(testUser.getPassword(), userDetails.getPassword());

        var expectedAuthorities = Set.of(
                new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"),
                new SimpleGrantedAuthority("ROLE_USER")
        );
        var actualAuthorities = userDetails.getAuthorities();

        assertEquals(expectedAuthorities.size(), actualAuthorities.size());
        for (GrantedAuthority authority : actualAuthorities) {
            assertEquals(true, expectedAuthorities.contains(authority));
        }

    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(mockUserRepository.findByUsername(NOT_EXISTENT_USERNAME)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            toTest.loadUserByUsername(NOT_EXISTENT_USERNAME);
        });
    }
}
