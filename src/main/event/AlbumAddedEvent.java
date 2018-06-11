package event;

import org.springframework.context.ApplicationEvent;

public class AlbumAddedEvent extends ApplicationEvent
{
    public AlbumAddedEvent(Object source)
    {
        super(source);
    }
}
