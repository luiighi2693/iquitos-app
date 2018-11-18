package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.TipoDeOperacionDeIngreso;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TipoDeOperacionDeIngreso entity.
 */
public interface TipoDeOperacionDeIngresoSearchRepository extends ElasticsearchRepository<TipoDeOperacionDeIngreso, Long> {
}
