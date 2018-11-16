package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.ContactoProveedor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContactoProveedor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactoProveedorRepository extends JpaRepository<ContactoProveedor, Long> {

}
