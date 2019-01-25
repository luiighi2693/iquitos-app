package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.ParametroSistema;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ParametroSistema entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametroSistemaRepository extends JpaRepository<ParametroSistema, Long> {
}
