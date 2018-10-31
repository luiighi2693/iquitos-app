package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.TipoDeDocumentoDeCompra;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoDeDocumentoDeCompra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDeDocumentoDeCompraRepository extends JpaRepository<TipoDeDocumentoDeCompra, Long> {

}
