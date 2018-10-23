package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Purchase;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Purchase entity.
 */
public interface PurchaseSearchRepository extends ElasticsearchRepository<Purchase, Long> {
}
