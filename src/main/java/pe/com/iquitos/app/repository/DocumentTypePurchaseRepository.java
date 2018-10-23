package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.DocumentTypePurchase;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DocumentTypePurchase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentTypePurchaseRepository extends JpaRepository<DocumentTypePurchase, Long> {

}
