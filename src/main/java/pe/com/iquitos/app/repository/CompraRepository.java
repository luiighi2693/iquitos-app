package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.Compra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Compra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    @Query(value = "select distinct compra from Compra compra left join fetch compra.productos",
        countQuery = "select count(distinct compra) from Compra compra")
    Page<Compra> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct compra from Compra compra left join fetch compra.productos")
    List<Compra> findAllWithEagerRelationships();

    @Query("select compra from Compra compra left join fetch compra.productos where compra.id =:id")
    Optional<Compra> findOneWithEagerRelationships(@Param("id") Long id);

}
