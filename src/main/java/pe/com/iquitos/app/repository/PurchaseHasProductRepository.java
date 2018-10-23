package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.PurchaseHasProduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PurchaseHasProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseHasProductRepository extends JpaRepository<PurchaseHasProduct, Long> {

}
