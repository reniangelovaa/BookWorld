package bg.softuni.bookworld.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterDTO {
    @NotBlank
    @Size(min = 5, max = 20)
    private String username;
    @NotEmpty
    @Size(min = 5, max = 50)
    private String fullName;
    @Email
    private String email;
    @Size(min = 5, max = 20)
    private String password;
    private String confirmPassword;

}
