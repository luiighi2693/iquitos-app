package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.CuentaProveedor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CuentaProveedor entity.
 */
public interface CuentaProveedorSearchRepository extends ElasticsearchRepository<CuentaProveedor, Long> {
}
