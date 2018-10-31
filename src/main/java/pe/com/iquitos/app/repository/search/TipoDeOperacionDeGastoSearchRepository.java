package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.TipoDeOperacionDeGasto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TipoDeOperacionDeGasto entity.
 */
public interface TipoDeOperacionDeGastoSearchRepository extends ElasticsearchRepository<TipoDeOperacionDeGasto, Long> {
}
