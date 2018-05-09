package manager.sorting;

import model.album.Album;
import model.album.AlbumSortingCriterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.AlbumRepository;

import java.util.List;

@Component
final public class ArtistSortingMethod extends AlbumSortingMethod
{
    @Autowired
    private AlbumRepository albumRepository;

    public ArtistSortingMethod()
    {
        this.albumSortingCriteria = AlbumSortingCriterias.ARTIST_NAME;
    }

    @Override
    public List<Album> sort()
    {
        return albumRepository.findAllGroupByArtist();
    }
}
