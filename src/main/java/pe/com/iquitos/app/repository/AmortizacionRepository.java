package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.Amortizacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;


/**
 * Spring Data  repository for the Amortizacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmortizacionRepository extends JpaRepository<Amortizacion, Long> {
    Optional<ArrayList<Amortizacion>> findAmortizacionsByVentaId(Long ventaId);
}
