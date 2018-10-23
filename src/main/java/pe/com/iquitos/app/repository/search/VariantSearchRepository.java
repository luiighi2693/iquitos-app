package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Variant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Variant entity.
 */
public interface VariantSearchRepository extends ElasticsearchRepository<Variant, Long> {
}
