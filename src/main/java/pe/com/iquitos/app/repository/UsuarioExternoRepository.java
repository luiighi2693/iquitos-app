package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.UsuarioExterno;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UsuarioExterno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioExternoRepository extends JpaRepository<UsuarioExterno, Long> {

}
