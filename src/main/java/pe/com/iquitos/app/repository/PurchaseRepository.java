package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Purchase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query(value = "select distinct purchase from Purchase purchase left join fetch purchase.products",
        countQuery = "select count(distinct purchase) from Purchase purchase")
    Page<Purchase> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct purchase from Purchase purchase left join fetch purchase.products")
    List<Purchase> findAllWithEagerRelationships();

    @Query("select purchase from Purchase purchase left join fetch purchase.products where purchase.id =:id")
    Optional<Purchase> findOneWithEagerRelationships(@Param("id") Long id);

}
