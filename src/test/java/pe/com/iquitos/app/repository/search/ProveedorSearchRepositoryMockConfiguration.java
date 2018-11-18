package pe.com.iquitos.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ProveedorSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ProveedorSearchRepositoryMockConfiguration {

    @MockBean
    private ProveedorSearchRepository mockProveedorSearchRepository;

}
