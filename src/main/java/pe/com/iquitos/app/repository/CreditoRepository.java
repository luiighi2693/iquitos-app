package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.Credito;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Credito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long> {

}
