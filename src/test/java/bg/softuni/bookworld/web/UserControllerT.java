package bg.softuni.bookworld.web;

import bg.softuni.bookworld.service.UserService;
import bg.softuni.bookworld.service.dto.UserProfileDTO;
import bg.softuni.bookworld.web.dto.UserRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testViewRegister() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("registerData"));
    }

    @Test
    public void testDoRegister() throws Exception {
        when(userService.isUsernameTaken(anyString())).thenReturn(false);
        when(userService.isEmailTaken(anyString())).thenReturn(false);
        when(userService.doPasswordsMatch(anyString(), anyString())).thenReturn(true);

        mockMvc.perform(post("/users/register")
                        .param("username", "testUser")
                        .param("fullName", "Test User")
                        .param("email", "testUser@example.com")
                        .param("password", "password")
                        .param("confirmPassword", "password")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));

        verify(userService, times(1)).register(any(UserRegisterDTO.class));
    }

    @Test
    public void testDoRegisterWithErrors() throws Exception {
        when(userService.isUsernameTaken(anyString())).thenReturn(true);
        when(userService.isEmailTaken(anyString())).thenReturn(false);
        when(userService.doPasswordsMatch(anyString(), anyString())).thenReturn(true);

        mockMvc.perform(post("/users/register")
                        .param("username", "testUser")
                        .param("fullName", "Test User")
                        .param("email", "testUser@example.com")
                        .param("password", "password")
                        .param("confirmPassword", "password")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("registerData"));
    }

    @Test
    public void testViewLogin() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("loginData"));
    }

    @Test
    public void testViewLoginError() throws Exception {
        mockMvc.perform(get("/users/login-error"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("showErrorMessage"))
                .andExpect(model().attributeExists("loginData"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testProfile() throws Exception {
        UserProfileDTO profileDTO = new UserProfileDTO();
        when(userService.getProfileData()).thenReturn(profileDTO);

        mockMvc.perform(get("/users/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attributeExists("profileData"))
                .andExpect(model().attribute("profileData", profileDTO));
    }
}
