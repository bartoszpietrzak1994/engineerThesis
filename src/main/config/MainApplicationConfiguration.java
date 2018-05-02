package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"main", "controller", "client", "manager", "repository"})
@EnableJpaRepositories(basePackages = "repository")
@PropertySource("classpath:user_interface.properties")
public class MainApplicationConfiguration
{
}
