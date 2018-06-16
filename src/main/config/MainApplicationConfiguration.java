package config;

import client.RecommendationsServiceImpl;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.RecommendationsService;

@Configuration
@ComponentScan(basePackages = {"main", "controller", "client", "manager", "repository", "validation", "util"})
@EnableJpaRepositories(basePackages = "repository")
@PropertySource("classpath:user_interface.properties")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MainApplicationConfiguration
{
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RecommendationsService recommendationsService()
    {
        return new RecommendationsServiceImpl();
    }
}
