package config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@ComponentScan(basePackages = {"main", "controller", "client", "manager", "repository"})
@EnableJpaRepositories(basePackages = "repository")
@PropertySource("classpath:user_interface.properties")
public class MainApplicationConfiguration
{
    public static final ApplicationContext applicationContext = new AnnotationConfigApplicationContext
            (MainApplicationConfiguration.class, PersistenceConfiguration.class);

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
