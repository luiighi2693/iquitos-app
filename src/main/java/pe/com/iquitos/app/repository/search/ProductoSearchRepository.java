package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Producto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Producto entity.
 */
public interface ProductoSearchRepository extends ElasticsearchRepository<Producto, Long> {
}
