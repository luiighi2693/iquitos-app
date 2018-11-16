package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Proveedor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    @Query(value = "select distinct proveedor from Proveedor proveedor left join fetch proveedor.cuentaProveedors left join fetch proveedor.contactoProveedors",
        countQuery = "select count(distinct proveedor) from Proveedor proveedor")
    Page<Proveedor> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct proveedor from Proveedor proveedor left join fetch proveedor.cuentaProveedors left join fetch proveedor.contactoProveedors")
    List<Proveedor> findAllWithEagerRelationships();

    @Query("select proveedor from Proveedor proveedor left join fetch proveedor.cuentaProveedors left join fetch proveedor.contactoProveedors where proveedor.id =:id")
    Optional<Proveedor> findOneWithEagerRelationships(@Param("id") Long id);

}
