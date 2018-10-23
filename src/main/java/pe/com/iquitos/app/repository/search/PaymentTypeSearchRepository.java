package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.PaymentType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PaymentType entity.
 */
public interface PaymentTypeSearchRepository extends ElasticsearchRepository<PaymentType, Long> {
}
