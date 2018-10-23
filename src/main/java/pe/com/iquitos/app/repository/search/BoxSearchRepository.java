package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Box;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Box entity.
 */
public interface BoxSearchRepository extends ElasticsearchRepository<Box, Long> {
}
