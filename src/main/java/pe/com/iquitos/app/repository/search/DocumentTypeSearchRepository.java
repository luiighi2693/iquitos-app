package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.DocumentType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DocumentType entity.
 */
public interface DocumentTypeSearchRepository extends ElasticsearchRepository<DocumentType, Long> {
}
