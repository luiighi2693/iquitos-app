package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.TipoDeOperacionDeIngreso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoDeOperacionDeIngreso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDeOperacionDeIngresoRepository extends JpaRepository<TipoDeOperacionDeIngreso, Long> {

}
