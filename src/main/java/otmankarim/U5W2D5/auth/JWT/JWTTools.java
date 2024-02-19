package otmankarim.U5W2D5.auth.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import otmankarim.U5W2D5.exceptions.UnauthorizedException;
import otmankarim.U5W2D5.user.User;

import java.util.Date;

@Service
public class JWTTools {
    @Value("${jwt.secret}")
    private String secret;

    //Creo il token che verrà dato al singolo user per tutta la durata del suo login
    //Avrà scadenza fino al logout oppure in linea di massima la scadenza sarà quella che avrò nella scadenza del token (expiration)
    public String createToken(User user) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis())) // Data di emissione del token (IAT)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Data di scadenza del token (Expiration Date)
                .subject(String.valueOf(user.getId())) // Subject ovvero a chi appartiene il token
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) // Firmo il token grazie alla mia chiave secret che rimane nascosta nel backend
                .compact();
    }

    public void verifyToken(String token) { // Dato un token mi lancia eccezioni in caso di token manipolato o scaduto
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Problemi col token! Effettua di nuovo il login!");
        }
    }

    public String extractIdFromToken(String token) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseSignedClaims(token).getPayload()
                .getSubject(); // Il subject è l'id dell'utente
    }

}
