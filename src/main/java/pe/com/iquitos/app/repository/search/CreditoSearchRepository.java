package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Credito;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Credito entity.
 */
public interface CreditoSearchRepository extends ElasticsearchRepository<Credito, Long> {
}
