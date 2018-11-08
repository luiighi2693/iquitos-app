package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Cliente;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Cliente entity.
 */
public interface ClienteSearchRepository extends ElasticsearchRepository<Cliente, Long> {
}
