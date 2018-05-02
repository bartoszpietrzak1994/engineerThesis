package integration;

import config.MainApplicationConfiguration;
import config.PersistenceConfiguration;
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
            (MainApplicationConfiguration.class, PersistenceConfiguration.class);
}