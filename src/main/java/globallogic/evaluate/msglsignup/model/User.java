package globallogic.evaluate.msglsignup.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Table(name = "users")
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;
    private String password;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user"))
    private List<Phone> phones;

}
