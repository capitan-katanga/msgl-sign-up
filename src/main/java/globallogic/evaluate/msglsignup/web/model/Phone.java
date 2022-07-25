package globallogic.evaluate.msglsignup.web.model;

import lombok.Data;

import javax.persistence.*;


@Table(name = "phones")
@Entity
@Data
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private long number;
    private int citycode;
    private String countrycode;

}
