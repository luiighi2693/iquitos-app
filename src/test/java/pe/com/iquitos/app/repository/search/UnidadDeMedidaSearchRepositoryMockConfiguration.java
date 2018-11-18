package pe.com.iquitos.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of UnidadDeMedidaSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class UnidadDeMedidaSearchRepositoryMockConfiguration {

    @MockBean
    private UnidadDeMedidaSearchRepository mockUnidadDeMedidaSearchRepository;

}
