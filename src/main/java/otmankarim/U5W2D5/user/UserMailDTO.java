package otmankarim.U5W2D5.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserMailDTO(
        @NotEmpty(message = "email is mandatory")
        @Email(message = "email is not valid")
        String email
) {
}
