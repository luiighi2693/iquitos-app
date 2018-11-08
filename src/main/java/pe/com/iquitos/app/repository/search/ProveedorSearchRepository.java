package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Proveedor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Proveedor entity.
 */
public interface ProveedorSearchRepository extends ElasticsearchRepository<Proveedor, Long> {
}
