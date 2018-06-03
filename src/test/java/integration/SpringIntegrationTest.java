package integration;

import config.PersistenceConfiguration;
import config.TestApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(
        classes = {TestApplicationConfiguration.class, PersistenceConfiguration.class},
        loader = AnnotationConfigContextLoader.class)
@Configuration
public class SpringIntegrationTest
{
}
