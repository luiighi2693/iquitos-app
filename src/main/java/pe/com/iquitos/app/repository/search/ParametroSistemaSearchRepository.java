package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.ParametroSistema;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ParametroSistema entity.
 */
public interface ParametroSistemaSearchRepository extends ElasticsearchRepository<ParametroSistema, Long> {
}
