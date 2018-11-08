package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.TipoDeDocumentoDeVenta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoDeDocumentoDeVenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDeDocumentoDeVentaRepository extends JpaRepository<TipoDeDocumentoDeVenta, Long> {

}
