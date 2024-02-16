package otmankarim.U5W2D5.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String avatar;

    public User(String username, String name, String surname, String email) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}
