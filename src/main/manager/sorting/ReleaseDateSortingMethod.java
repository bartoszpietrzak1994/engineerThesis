package manager.sorting;

import model.album.Album;
import model.album.AlbumSortingCriterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.AlbumRepository;

import java.util.List;

@Component
public class ReleaseDateSortingMethod extends AlbumSortingMethod
{
    @Autowired
    private AlbumRepository albumRepository;

    public ReleaseDateSortingMethod()
    {
        this.albumSortingCriteria = AlbumSortingCriterias.RELEASE_DATE;
    }

    @Override
    public List<Album> sort()
    {
        return albumRepository.findAllGroupByReleaseDate();
    }
}
