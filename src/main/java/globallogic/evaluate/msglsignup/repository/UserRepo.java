package globallogic.evaluate.msglsignup.repository;

import globallogic.evaluate.msglsignup.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
