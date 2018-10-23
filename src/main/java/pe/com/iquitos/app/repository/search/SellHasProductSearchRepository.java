package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.SellHasProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SellHasProduct entity.
 */
public interface SellHasProductSearchRepository extends ElasticsearchRepository<SellHasProduct, Long> {
}
