package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.ProductoDetalle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ProductoDetalle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoDetalleRepository extends JpaRepository<ProductoDetalle, Long> {

    @Query(value = "select distinct producto_detalle from ProductoDetalle producto_detalle left join fetch producto_detalle.variantes left join fetch producto_detalle.productos",
        countQuery = "select count(distinct producto_detalle) from ProductoDetalle producto_detalle")
    Page<ProductoDetalle> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct producto_detalle from ProductoDetalle producto_detalle left join fetch producto_detalle.variantes left join fetch producto_detalle.productos")
    List<ProductoDetalle> findAllWithEagerRelationships();

    @Query("select producto_detalle from ProductoDetalle producto_detalle left join fetch producto_detalle.variantes left join fetch producto_detalle.productos where producto_detalle.id =:id")
    Optional<ProductoDetalle> findOneWithEagerRelationships(@Param("id") Long id);

}
