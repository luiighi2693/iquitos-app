package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.ProviderPayment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProviderPayment entity.
 */
public interface ProviderPaymentSearchRepository extends ElasticsearchRepository<ProviderPayment, Long> {
}
