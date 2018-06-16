package aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.AlbumRepository;

@Aspect
@Component
public class AlbumRepositoryFlusher
{
    private AlbumRepository albumRepository;

    @Autowired
    public AlbumRepositoryFlusher(AlbumRepository albumRepository)
    {
        this.albumRepository = albumRepository;
    }

    @After("execution(* repository.AlbumRepository.save(..))")
    public void afterEntitySaved()
    {
        albumRepository.flush();
    }
}
