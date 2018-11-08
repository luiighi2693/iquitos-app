package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.EstatusDeProductoEntregado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EstatusDeProductoEntregado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstatusDeProductoEntregadoRepository extends JpaRepository<EstatusDeProductoEntregado, Long> {

}
