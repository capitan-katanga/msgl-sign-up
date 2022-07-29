package globallogic.evaluate.msglsignup.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Table(name = "phones")
@Entity
@Data
@NoArgsConstructor
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private long number;
    private int citycode;
    private String countrycode;

}
