package otmankarim.U5W2D5.auth.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import otmankarim.U5W2D5.exceptions.UnauthorizedException;
import otmankarim.U5W2D5.user.User;
import otmankarim.U5W2D5.user.UserService;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UserService usersService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Il codice di questo metodo verrà eseguito a ogni richiesta che richieda di essere autenticati
        // Cose da fare:

        // 1. Verifichiamo se la richiesta effettivamente contiene un Authorization Header, se non c'è --> 401
        String authHeader = request.getHeader("Authorization"); // Bearer stringLungaCheHaIlTokenGenerato
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Please put the authorization token in header");

        // 2. Se c'è estraiamo il token dall'header rimuovendo la parte iniziale "Bearer " con un substring
        String accessToken = authHeader.substring(7);

        //System.out.println("ACCESS TOKEN " + accessToken);

        // 3. Verifichiamo se il token è stato manipolato (verifica signature) e se non è scaduto (verifica Expiration Date)
        jwtTools.verifyToken(accessToken);

        // 4. Se è tutto OK proseguiamo con la catena fino ad arrivare all'endpoint

        // 4.1 Cerco l'utente nel DB (l'id sta nel token...)
        String id = jwtTools.extractIdFromToken(accessToken);
        User user = usersService.findById(Long.parseLong(id));

        // 4.2 Devo informare Spring Security che l'utente è autenticato (se non faccio questo step riceverò "403 Forbidden Error" come risposta)
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);

        // Ci servirà domani per l'AUTORIZZAZIONE
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4.3 Proseguo ora col prossimo elemento della catena
        filterChain.doFilter(request, response); // va al prossimo elemento della catena

        // 5. Se non è OK --> 401
    }

    // Disabilito il filtro per determinate richieste tipo Login o Register (non devono richiedere token)
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Uso questo metodo per specificare in che situazioni il filtro NON DEVE FILTRARE (non deve chiedere il token)
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
        // Se l'URL della richiesta corrente corrisponde a /auth/qualsiasicosa, allora il filtro non entra in azione
    }
}
