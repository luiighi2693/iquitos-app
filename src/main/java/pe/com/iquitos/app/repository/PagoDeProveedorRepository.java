package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.PagoDeProveedor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PagoDeProveedor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PagoDeProveedorRepository extends JpaRepository<PagoDeProveedor, Long> {

}
