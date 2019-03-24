package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Venta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Query(value = "select distinct venta from Venta venta left join fetch venta.productos left join fetch venta.productoDetalles left join fetch venta.amortizacions",
        countQuery = "select count(distinct venta) from Venta venta")
    Page<Venta> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct venta from Venta venta left join fetch venta.productos left join fetch venta.productoDetalles left join fetch venta.amortizacions")
    List<Venta> findAllWithEagerRelationships();

    @Query("select venta from Venta venta left join fetch venta.productos left join fetch venta.productoDetalles left join fetch venta.amortizacions where venta.id =:id")
    Optional<Venta> findOneWithEagerRelationships(@Param("id") Long id);

    List<Venta> findAllByFechaBetween(LocalDate start, LocalDate end);
}
