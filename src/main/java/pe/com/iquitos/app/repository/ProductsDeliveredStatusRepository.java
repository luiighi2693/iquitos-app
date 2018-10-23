package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.ProductsDeliveredStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductsDeliveredStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductsDeliveredStatusRepository extends JpaRepository<ProductsDeliveredStatus, Long> {

}
