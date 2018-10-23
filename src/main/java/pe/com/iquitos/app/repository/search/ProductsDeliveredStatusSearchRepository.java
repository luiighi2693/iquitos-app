package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.ProductsDeliveredStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductsDeliveredStatus entity.
 */
public interface ProductsDeliveredStatusSearchRepository extends ElasticsearchRepository<ProductsDeliveredStatus, Long> {
}
