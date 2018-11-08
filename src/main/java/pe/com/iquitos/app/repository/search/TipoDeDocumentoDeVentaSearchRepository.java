package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.TipoDeDocumentoDeVenta;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TipoDeDocumentoDeVenta entity.
 */
public interface TipoDeDocumentoDeVentaSearchRepository extends ElasticsearchRepository<TipoDeDocumentoDeVenta, Long> {
}
