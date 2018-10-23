package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.ProviderPayment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProviderPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProviderPaymentRepository extends JpaRepository<ProviderPayment, Long> {

}
