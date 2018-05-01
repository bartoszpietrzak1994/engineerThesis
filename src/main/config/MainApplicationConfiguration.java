package config;

import client.AuthenticationServiceImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackageClasses = AuthenticationServiceImpl.class)
@PropertySource("classpath:user_interface.properties")
public class MainApplicationConfiguration
{
}
