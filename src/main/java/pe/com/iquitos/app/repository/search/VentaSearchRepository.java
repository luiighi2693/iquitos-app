package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Venta;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Venta entity.
 */
public interface VentaSearchRepository extends ElasticsearchRepository<Venta, Long> {
}
