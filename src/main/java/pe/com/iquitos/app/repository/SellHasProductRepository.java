package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.SellHasProduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SellHasProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SellHasProductRepository extends JpaRepository<SellHasProduct, Long> {

}
