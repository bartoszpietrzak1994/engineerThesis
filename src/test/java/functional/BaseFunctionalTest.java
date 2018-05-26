package functional;

import config.PersistenceConfiguration;
import config.TestApplicationConfiguration;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import repository.AlbumRepository;
import repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestApplicationConfiguration.class, PersistenceConfiguration.class},
        loader = AnnotationConfigContextLoader.class)
public class BaseFunctionalTest
{
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp()
    {
        albumRepository.deleteAll();
        userRepository.deleteAll();
    }
}
