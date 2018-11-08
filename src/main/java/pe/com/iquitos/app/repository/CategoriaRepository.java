package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.Categoria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Categoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
