package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.EstatusDeCompra;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EstatusDeCompra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstatusDeCompraRepository extends JpaRepository<EstatusDeCompra, Long> {

}
