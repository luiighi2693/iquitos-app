package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.Amortizacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import pe.com.iquitos.app.domain.TipoDeDocumentoDeVenta;

import java.util.Optional;


/**
 * Spring Data  repository for the Amortizacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmortizacionRepository extends JpaRepository<Amortizacion, Long> {
    Optional<Long> countAmortizacionsByTipoDeDocumentoDeVentaId(Long tipoDeDocumentoDeVenta);
}
