package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.ProductosRelacionadosTags;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductosRelacionadosTags entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductosRelacionadosTagsRepository extends JpaRepository<ProductosRelacionadosTags, Long> {

}
