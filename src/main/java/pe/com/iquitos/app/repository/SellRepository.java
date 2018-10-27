package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.Sell;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Sell entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SellRepository extends JpaRepository<Sell, Long> {

    @Query(value = "select distinct sell from Sell sell left join fetch sell.products",
        countQuery = "select count(distinct sell) from Sell sell")
    Page<Sell> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct sell from Sell sell left join fetch sell.products")
    List<Sell> findAllWithEagerRelationships();

    @Query("select sell from Sell sell left join fetch sell.products where sell.id =:id")
    Optional<Sell> findOneWithEagerRelationships(@Param("id") Long id);

}
