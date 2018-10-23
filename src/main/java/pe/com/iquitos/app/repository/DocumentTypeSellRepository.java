package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.DocumentTypeSell;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DocumentTypeSell entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentTypeSellRepository extends JpaRepository<DocumentTypeSell, Long> {

}
