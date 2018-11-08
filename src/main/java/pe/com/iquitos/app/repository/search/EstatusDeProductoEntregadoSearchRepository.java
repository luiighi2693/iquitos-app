package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.EstatusDeProductoEntregado;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EstatusDeProductoEntregado entity.
 */
public interface EstatusDeProductoEntregadoSearchRepository extends ElasticsearchRepository<EstatusDeProductoEntregado, Long> {
}
