package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.PurchaseStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PurchaseStatus entity.
 */
public interface PurchaseStatusSearchRepository extends ElasticsearchRepository<PurchaseStatus, Long> {
}
