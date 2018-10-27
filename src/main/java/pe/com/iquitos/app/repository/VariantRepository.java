package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.Variant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Variant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VariantRepository extends JpaRepository<Variant, Long> {

    @Query(value = "select distinct variant from Variant variant left join fetch variant.products",
        countQuery = "select count(distinct variant) from Variant variant")
    Page<Variant> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct variant from Variant variant left join fetch variant.products")
    List<Variant> findAllWithEagerRelationships();

    @Query("select variant from Variant variant left join fetch variant.products where variant.id =:id")
    Optional<Variant> findOneWithEagerRelationships(@Param("id") Long id);

}
