package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.TipoDeDocumento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoDeDocumento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDeDocumentoRepository extends JpaRepository<TipoDeDocumento, Long> {

}
