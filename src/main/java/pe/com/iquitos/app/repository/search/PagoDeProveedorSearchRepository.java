package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.PagoDeProveedor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PagoDeProveedor entity.
 */
public interface PagoDeProveedorSearchRepository extends ElasticsearchRepository<PagoDeProveedor, Long> {
}
