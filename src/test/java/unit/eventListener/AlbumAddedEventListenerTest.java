package unit.eventListener;

import controller.MainWindowController;
import event.AlbumAddedEvent;
import eventListener.AlbumAddedEventListener;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class AlbumAddedEventListenerTest
{
    private AlbumAddedEventListener albumAddedEventListener;

    private MainWindowController mainWindowController;

    @Before
    public void setUp()
    {
        mainWindowController = mock(MainWindowController.class);
    }

    @Test
    public void itRefreshesAlbumList()
    {
        // GIVEN
        albumAddedEventListener = new AlbumAddedEventListener(mainWindowController);
        AlbumAddedEvent albumAddedEvent = new AlbumAddedEvent(new Object());

        // WHEN
        albumAddedEventListener.onApplicationEvent(albumAddedEvent);

        // THEN
        verify(mainWindowController, times(1)).loadUserAlbums();
    }
}
