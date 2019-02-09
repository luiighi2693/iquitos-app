package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.Amortizacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Amortizacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmortizacionRepository extends JpaRepository<Amortizacion, Long> {

}
