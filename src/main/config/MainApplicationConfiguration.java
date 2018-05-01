package config;

import client.AuthenticationServiceImpl;
import manager.AuthenticationProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackageClasses = {AuthenticationServiceImpl.class, AuthenticationProvider.class})
@PropertySource("classpath:user_interface.properties")
public class MainApplicationConfiguration
{
}
