package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Empleado;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Empleado entity.
 */
public interface EmpleadoSearchRepository extends ElasticsearchRepository<Empleado, Long> {
}
