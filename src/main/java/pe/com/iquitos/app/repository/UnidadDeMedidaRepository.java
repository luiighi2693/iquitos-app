package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.UnidadDeMedida;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UnidadDeMedida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadDeMedidaRepository extends JpaRepository<UnidadDeMedida, Long> {

}
