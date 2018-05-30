package manager.sorting;

import model.album.Album;
import model.album.AlbumOrderingCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.AlbumRepository;

import java.util.List;

@Component
public class ReleaseDateSortingMethod extends AlbumSortingMethod
{
    private AlbumRepository albumRepository;

    @Autowired
    public ReleaseDateSortingMethod(AlbumRepository albumRepository)
    {
        this.albumRepository = albumRepository;
    }

    public ReleaseDateSortingMethod()
    {
        this.albumSortingCriteria = AlbumOrderingCriteria.RELEASE_DATE;
    }

    @Override
    public List<Album> sort()
    {
        return albumRepository.findAllOrderByReleaseDate();
    }
}
