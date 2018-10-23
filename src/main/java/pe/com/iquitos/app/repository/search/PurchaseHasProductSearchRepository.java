package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.PurchaseHasProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PurchaseHasProduct entity.
 */
public interface PurchaseHasProductSearchRepository extends ElasticsearchRepository<PurchaseHasProduct, Long> {
}
