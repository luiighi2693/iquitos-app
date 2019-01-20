package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.ProductoDetalle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductoDetalle entity.
 */
public interface ProductoDetalleSearchRepository extends ElasticsearchRepository<ProductoDetalle, Long> {
}
