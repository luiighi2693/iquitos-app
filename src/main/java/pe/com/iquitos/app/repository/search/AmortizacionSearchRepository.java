package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Amortizacion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Amortizacion entity.
 */
public interface AmortizacionSearchRepository extends ElasticsearchRepository<Amortizacion, Long> {
}
