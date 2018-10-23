package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Credit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Credit entity.
 */
public interface CreditSearchRepository extends ElasticsearchRepository<Credit, Long> {
}
