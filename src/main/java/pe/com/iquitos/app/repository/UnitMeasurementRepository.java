package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.UnitMeasurement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UnitMeasurement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnitMeasurementRepository extends JpaRepository<UnitMeasurement, Long> {

}
