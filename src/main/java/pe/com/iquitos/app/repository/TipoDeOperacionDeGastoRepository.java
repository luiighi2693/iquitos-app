package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.TipoDeOperacionDeGasto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoDeOperacionDeGasto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDeOperacionDeGastoRepository extends JpaRepository<TipoDeOperacionDeGasto, Long> {

}
