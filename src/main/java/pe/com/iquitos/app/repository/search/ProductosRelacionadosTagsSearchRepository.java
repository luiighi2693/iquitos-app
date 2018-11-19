package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.ProductosRelacionadosTags;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductosRelacionadosTags entity.
 */
public interface ProductosRelacionadosTagsSearchRepository extends ElasticsearchRepository<ProductosRelacionadosTags, Long> {
}
