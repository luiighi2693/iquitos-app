package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.UserLogin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserLogin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

}
