package pe.com.iquitos.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ConceptOperationInputSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ConceptOperationInputSearchRepositoryMockConfiguration {

    @MockBean
    private ConceptOperationInputSearchRepository mockConceptOperationInputSearchRepository;

}
