package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.UnidadDeMedida;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UnidadDeMedida entity.
 */
public interface UnidadDeMedidaSearchRepository extends ElasticsearchRepository<UnidadDeMedida, Long> {
}
