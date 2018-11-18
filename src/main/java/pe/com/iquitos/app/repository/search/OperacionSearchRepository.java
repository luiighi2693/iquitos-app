package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Operacion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Operacion entity.
 */
public interface OperacionSearchRepository extends ElasticsearchRepository<Operacion, Long> {
}
