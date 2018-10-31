package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.Operacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Operacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperacionRepository extends JpaRepository<Operacion, Long> {

}
