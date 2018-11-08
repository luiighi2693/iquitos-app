package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Caja;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Caja entity.
 */
public interface CajaSearchRepository extends ElasticsearchRepository<Caja, Long> {
}
