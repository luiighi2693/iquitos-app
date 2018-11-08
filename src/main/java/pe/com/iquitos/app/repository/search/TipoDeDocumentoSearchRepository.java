package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.TipoDeDocumento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TipoDeDocumento entity.
 */
public interface TipoDeDocumentoSearchRepository extends ElasticsearchRepository<TipoDeDocumento, Long> {
}
