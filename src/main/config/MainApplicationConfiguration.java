package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "main")
@PropertySource("classpath:user_interface.properties")
public class MainApplicationConfiguration
{
}
