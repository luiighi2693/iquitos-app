package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.ProviderAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProviderAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProviderAccountRepository extends JpaRepository<ProviderAccount, Long> {

}
