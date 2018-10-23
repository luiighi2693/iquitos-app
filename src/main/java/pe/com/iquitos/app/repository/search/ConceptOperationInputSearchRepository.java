package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.ConceptOperationInput;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ConceptOperationInput entity.
 */
public interface ConceptOperationInputSearchRepository extends ElasticsearchRepository<ConceptOperationInput, Long> {
}
