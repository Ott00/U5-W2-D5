package otmankarim.U5W2D5.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class BCryptConfig {
    @Bean
    PasswordEncoder getBCrypt() {
        return new BCryptPasswordEncoder(11);
        // 11 è il numero di ROUNDS, ovvero quante volte viene eseguito l'algoritmo. Questo valore
        // è utile per impostare la velocità di esecuzione di BCrypt. Più veloce è BCrypt, meno sicure saranno
        // le password, e ovviamente viceversa. Di contro però più lento è l'algoritmo, peggiore sarà la User Experience.
        // Bisogna trovare il giusto bilanciamento tra le 2.
        // 11 significa che l'algoritmo verrà eseguito 2^11 volte cioè 2048 volte. Su un computer di prestazioni medie
        // vuol dire circa 100ms
    }
}
