package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Compra;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Compra entity.
 */
public interface CompraSearchRepository extends ElasticsearchRepository<Compra, Long> {
}
