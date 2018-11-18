package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.TipoDePago;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoDePago entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDePagoRepository extends JpaRepository<TipoDePago, Long> {

}
