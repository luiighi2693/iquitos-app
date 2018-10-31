package pe.com.iquitos.app.repository.search;

import pe.com.iquitos.app.domain.Pedido;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Pedido entity.
 */
public interface PedidoSearchRepository extends ElasticsearchRepository<Pedido, Long> {
}
