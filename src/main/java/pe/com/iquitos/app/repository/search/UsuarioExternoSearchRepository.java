package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.UsuarioExterno;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UsuarioExterno entity.
 */
public interface UsuarioExternoSearchRepository extends ElasticsearchRepository<UsuarioExterno, Long> {
}
