package pe.com.iquitos.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of SellHasProductSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SellHasProductSearchRepositoryMockConfiguration {

    @MockBean
    private SellHasProductSearchRepository mockSellHasProductSearchRepository;

}
