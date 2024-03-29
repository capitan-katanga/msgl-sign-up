package globallogic.evaluate.msglsignup.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Table(name = "users")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private boolean isActive;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user"))
    private List<Phone> phones;

}
