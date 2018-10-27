package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.UserLogin;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UserLogin entity.
 */
public interface UserLoginSearchRepository extends ElasticsearchRepository<UserLogin, Long> {
}
