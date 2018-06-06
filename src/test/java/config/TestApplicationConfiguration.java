package config;

import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.RecommendationsService;
import service.TestRecommendationsService;

@Configuration
@ComponentScan(basePackages = {"main", "controller", "client", "manager", "repository", "util", "validation"})
@EnableJpaRepositories(basePackages = "repository")
@PropertySource("classpath:user_interface.properties")
public class TestApplicationConfiguration
{
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public RecommendationsService recommendationsService()
    {
        return new TestRecommendationsService();
    }
}
