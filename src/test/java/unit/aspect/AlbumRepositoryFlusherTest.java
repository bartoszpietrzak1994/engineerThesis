package unit.aspect;

import aspect.AlbumRepositoryFlusher;
import model.album.Album;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import repository.AlbumRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AlbumRepositoryFlusherTest
{
    private AlbumRepository albumRepository;

    private AlbumRepositoryFlusher albumRepositoryFlusher;

    @Before
    public void setUp()
    {
        albumRepository = mock(AlbumRepository.class);
    }

    @Test
    public void itFlushesRepositoryAfterSavingNewEntity()
    {
        // GIVEN
        AspectJProxyFactory factory = new AspectJProxyFactory(albumRepository);
        AlbumRepositoryFlusher aspect = new AlbumRepositoryFlusher(albumRepository);
        factory.addAspect(aspect);
        AlbumRepository proxy = factory.getProxy();

        // WHEN
        proxy.save(new Album());

        // THEN
        verify(albumRepository, times(1)).flush();
    }
}
