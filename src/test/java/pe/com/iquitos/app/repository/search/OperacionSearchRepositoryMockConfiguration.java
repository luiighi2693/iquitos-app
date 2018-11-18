package pe.com.iquitos.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of OperacionSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class OperacionSearchRepositoryMockConfiguration {

    @MockBean
    private OperacionSearchRepository mockOperacionSearchRepository;

}
