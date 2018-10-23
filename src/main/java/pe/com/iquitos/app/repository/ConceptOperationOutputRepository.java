package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.ConceptOperationOutput;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConceptOperationOutput entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptOperationOutputRepository extends JpaRepository<ConceptOperationOutput, Long> {

}
