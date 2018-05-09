package manager.sorting;

import model.album.Album;
import model.album.AlbumOrderingCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.AlbumRepository;

import java.util.List;

@Component
final public class RatingSortingMethod extends AlbumSortingMethod
{
    @Autowired
    private AlbumRepository albumRepository;

    public RatingSortingMethod()
    {
        this.albumSortingCriteria = AlbumOrderingCriteria.RATING;
    }

    @Override
    public List<Album> sort()
    {
        return albumRepository.findAllOrderByRating();
    }
}
