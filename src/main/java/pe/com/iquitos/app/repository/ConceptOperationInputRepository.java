package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.ConceptOperationInput;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConceptOperationInput entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptOperationInputRepository extends JpaRepository<ConceptOperationInput, Long> {

}
