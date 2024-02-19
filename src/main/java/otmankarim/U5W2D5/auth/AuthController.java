package otmankarim.U5W2D5.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import otmankarim.U5W2D5.auth.TDO.LoginResponseDTO;
import otmankarim.U5W2D5.auth.TDO.UserLoginTDO;
import otmankarim.U5W2D5.auth.service.AuthService;
import otmankarim.U5W2D5.exceptions.BadRequestException;
import otmankarim.U5W2D5.user.User;
import otmankarim.U5W2D5.user.UserDTO;
import otmankarim.U5W2D5.user.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody UserLoginTDO payload) {
        return new LoginResponseDTO(authService.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // Status Code 201
    public User register(@RequestBody @Validated UserDTO newUser, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        // Se non ci sono errori salvo lo user
        return this.userService.save(newUser);
    }
}
