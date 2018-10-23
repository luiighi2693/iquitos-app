package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Sell;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Sell entity.
 */
public interface SellSearchRepository extends ElasticsearchRepository<Sell, Long> {
}
