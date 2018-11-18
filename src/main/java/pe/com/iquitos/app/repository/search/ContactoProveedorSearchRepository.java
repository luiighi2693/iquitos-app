package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.ContactoProveedor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ContactoProveedor entity.
 */
public interface ContactoProveedorSearchRepository extends ElasticsearchRepository<ContactoProveedor, Long> {
}
