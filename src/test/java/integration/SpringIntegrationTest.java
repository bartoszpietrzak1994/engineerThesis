package integration;

import config.MainApplicationConfiguration;
import config.PersistenceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(
        classes = {MainApplicationConfiguration.class, PersistenceConfiguration.class},
        loader = AnnotationConfigContextLoader.class)
@ComponentScan
@Configuration
public class SpringIntegrationTest
{
}
