package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.ProviderAccount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProviderAccount entity.
 */
public interface ProviderAccountSearchRepository extends ElasticsearchRepository<ProviderAccount, Long> {
}
