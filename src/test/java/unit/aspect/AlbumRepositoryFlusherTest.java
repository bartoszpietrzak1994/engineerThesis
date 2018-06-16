package unit.aspect;

import aspect.AlbumRepositoryFlusher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import repository.AlbumRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AlbumRepositoryFlusherTest
{
    @Mock
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
        albumRepositoryFlusher = new AlbumRepositoryFlusher(albumRepository);

        // WHEN
        albumRepositoryFlusher.afterEntitySaved();

        // THEN
        verify(albumRepository, times(1)).flush();
    }
}
