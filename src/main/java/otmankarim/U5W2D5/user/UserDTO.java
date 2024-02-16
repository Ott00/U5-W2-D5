package otmankarim.U5W2D5.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotEmpty(message = "username is mandatory")
        @Size(min = 5, message = "username must be at least 5 characters")
        String username,
        @NotEmpty(message = "name is mandatory")
        @Size(min = 3, message = "name must be at least 3 characters")
        String name,
        @NotEmpty(message = "surname is mandatory")
        @Size(min = 3, message = "surname must be at least 3 characters")
        String surname,
        @NotEmpty(message = "email is mandatory")
        @Email(message = "email is not valid")
        String email
) {
}
