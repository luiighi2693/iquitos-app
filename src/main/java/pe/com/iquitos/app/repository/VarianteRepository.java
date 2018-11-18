package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.Variante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Variante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VarianteRepository extends JpaRepository<Variante, Long> {

    @Query(value = "select distinct variante from Variante variante left join fetch variante.productos",
        countQuery = "select count(distinct variante) from Variante variante")
    Page<Variante> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct variante from Variante variante left join fetch variante.productos")
    List<Variante> findAllWithEagerRelationships();

    @Query("select variante from Variante variante left join fetch variante.productos where variante.id =:id")
    Optional<Variante> findOneWithEagerRelationships(@Param("id") Long id);

}
