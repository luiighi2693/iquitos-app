package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.UnitMeasurement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UnitMeasurement entity.
 */
public interface UnitMeasurementSearchRepository extends ElasticsearchRepository<UnitMeasurement, Long> {
}
