package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.ConceptOperationOutput;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ConceptOperationOutput entity.
 */
public interface ConceptOperationOutputSearchRepository extends ElasticsearchRepository<ConceptOperationOutput, Long> {
}
