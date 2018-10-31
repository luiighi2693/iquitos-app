package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.TipoDeDocumentoDeCompra;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TipoDeDocumentoDeCompra entity.
 */
public interface TipoDeDocumentoDeCompraSearchRepository extends ElasticsearchRepository<TipoDeDocumentoDeCompra, Long> {
}
