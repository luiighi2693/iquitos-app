package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Categoria;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Categoria entity.
 */
public interface CategoriaSearchRepository extends ElasticsearchRepository<Categoria, Long> {
}
