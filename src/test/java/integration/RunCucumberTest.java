package integration;

import config.PersistenceConfiguration;
import config.TestApplicationConfiguration;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"})
final public class RunCucumberTest
{
    @Rule
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext
            (TestApplicationConfiguration.class, PersistenceConfiguration.class);
}