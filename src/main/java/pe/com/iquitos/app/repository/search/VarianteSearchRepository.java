package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Variante;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Variante entity.
 */
public interface VarianteSearchRepository extends ElasticsearchRepository<Variante, Long> {
}
