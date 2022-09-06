package globallogic.evaluate.msglsignup;

import globallogic.evaluate.msglsignup.model.Phone;
import globallogic.evaluate.msglsignup.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class DataMock {

    public static User createUser01(){
        return User.builder().id(1).created(LocalDateTime.now()).lastLogin(null).isActive(true)
                .name("ignacio").email("ignacioencizo@gmail.com").password("Ignacio123")
                .phones(new ArrayList<>(Collections.singletonList(createPhone01()))).build();
    }

    public static Phone createPhone01(){
        return Phone.builder().id(1).number(3512616820L).citycode(5000).countrycode("+54").build();
    }
}
