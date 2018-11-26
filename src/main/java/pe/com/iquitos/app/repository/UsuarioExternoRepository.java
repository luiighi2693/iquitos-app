package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.UsuarioExterno;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.stream.IntStream;


/**
 * Spring Data  repository for the UsuarioExterno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioExternoRepository extends JpaRepository<UsuarioExterno, Long> {

    Optional<UsuarioExterno> findByDni(@NotNull Integer dni);
}
