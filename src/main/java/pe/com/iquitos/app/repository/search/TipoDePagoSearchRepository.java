package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.TipoDePago;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TipoDePago entity.
 */
public interface TipoDePagoSearchRepository extends ElasticsearchRepository<TipoDePago, Long> {
}
