package globallogic.evaluate.msglsignup.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;


@Table(name = "phones")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneEntity {

    @Id
    @Column(name = "phone_id")
    @Type(type = "uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private long number;
    @Column(name = "city_code")
    private int cityCode;
    @Column(name = "country_code")
    private String countryCode;

}
