package eventListener;

import controller.MainWindowController;
import event.AlbumAddedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AlbumAddedEventListener implements ApplicationListener<AlbumAddedEvent>
{
    private MainWindowController mainWindowController;

    @Autowired
    public AlbumAddedEventListener(MainWindowController mainWindowController)
    {
        this.mainWindowController = mainWindowController;
    }

    @Override
    public void onApplicationEvent(AlbumAddedEvent albumAddedEvent)
    {
        mainWindowController.loadUserAlbums();
    }
}
