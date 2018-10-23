package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.Variant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Variant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VariantRepository extends JpaRepository<Variant, Long> {

}
