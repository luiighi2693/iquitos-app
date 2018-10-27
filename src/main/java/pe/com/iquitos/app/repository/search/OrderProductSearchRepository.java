package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.OrderProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OrderProduct entity.
 */
public interface OrderProductSearchRepository extends ElasticsearchRepository<OrderProduct, Long> {
}
