package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.PurchaseStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PurchaseStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseStatusRepository extends JpaRepository<PurchaseStatus, Long> {

}
