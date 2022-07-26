package globallogic.evaluate.msglsignup.model;

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

    public void setNumber(long number) {
        this.number = number;
    }
}
