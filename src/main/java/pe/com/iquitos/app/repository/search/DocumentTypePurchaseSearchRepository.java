package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.DocumentTypePurchase;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DocumentTypePurchase entity.
 */
public interface DocumentTypePurchaseSearchRepository extends ElasticsearchRepository<DocumentTypePurchase, Long> {
}
