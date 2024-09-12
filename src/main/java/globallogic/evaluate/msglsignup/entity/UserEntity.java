package globallogic.evaluate.msglsignup.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Table(name = "users")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @Column(name = "user_id")
    @Type(type = "uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private LocalDateTime created;
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    @Column(name = "is_active")
    private boolean isActive;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user"))
    private List<PhoneEntity> phoneEntities;

}
