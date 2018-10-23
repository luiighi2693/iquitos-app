package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.Amortization;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Amortization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmortizationRepository extends JpaRepository<Amortization, Long> {

}
