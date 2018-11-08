package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.EstatusDeCompra;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EstatusDeCompra entity.
 */
public interface EstatusDeCompraSearchRepository extends ElasticsearchRepository<EstatusDeCompra, Long> {
}
