package manager.sorting;

import model.album.Album;
import model.album.AlbumOrderingCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.AlbumRepository;

import java.util.List;

@Component
final public class RatingDateSortingMethod extends AlbumSortingMethod
{
    @Autowired
    private AlbumRepository albumRepository;

    private AlbumOrderingCriteria albumSortingCriteria;

    public RatingDateSortingMethod()
    {
        this.albumSortingCriteria = AlbumOrderingCriteria.RATING_DATE;
    }

    @Override
    public List<Album> sort()
    {
        return albumRepository.findAllOrderByRatingDate();
    }
}
