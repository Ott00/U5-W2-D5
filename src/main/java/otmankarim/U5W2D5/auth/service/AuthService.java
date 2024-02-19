package otmankarim.U5W2D5.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otmankarim.U5W2D5.auth.JWT.JWTTools;
import otmankarim.U5W2D5.auth.TDO.UserLoginTDO;
import otmankarim.U5W2D5.exceptions.UnauthorizedException;
import otmankarim.U5W2D5.user.User;
import otmankarim.U5W2D5.user.UserService;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTTools jwtTools;

    public String authenticateUserAndGenerateToken(UserLoginTDO payload) {
        // 1. Controllo le credenziali
        User user = userService.findByEmail(payload.email());   //Se esiste un utente con questa mail
        if (user.getPassword().equals(payload.password())) {    //e se la password coincide con quella nel db
            // 2. Se tutto è OK, genero un token
            // 3. Torno il token come risposta
            return jwtTools.createToken(user);
        } else {
            // 4. Se le credenziali non sono OK --> 401 (Unauthorized)
            throw new UnauthorizedException("Wrong credentials!");
        }


    }
}
