package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.Caja;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Caja entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CajaRepository extends JpaRepository<Caja, Long> {

}
