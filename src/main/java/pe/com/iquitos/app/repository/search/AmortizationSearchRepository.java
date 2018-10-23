package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Amortization;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Amortization entity.
 */
public interface AmortizationSearchRepository extends ElasticsearchRepository<Amortization, Long> {
}
