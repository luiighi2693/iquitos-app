package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.DocumentTypeSell;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DocumentTypeSell entity.
 */
public interface DocumentTypeSellSearchRepository extends ElasticsearchRepository<DocumentTypeSell, Long> {
}
