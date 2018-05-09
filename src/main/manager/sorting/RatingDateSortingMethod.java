package manager.sorting;

import model.album.Album;
import model.album.AlbumSortingCriterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.AlbumRepository;

import java.util.List;

@Component
final public class RatingDateSortingMethod extends AlbumSortingMethod
{
    @Autowired
    private AlbumRepository albumRepository;

    private AlbumSortingCriterias albumSortingCriteria;

    public RatingDateSortingMethod()
    {
        this.albumSortingCriteria = AlbumSortingCriterias.RATING_DATE;
    }

    @Override
    public List<Album> sort()
    {
        return albumRepository.findAllGroupByRatingDate();
    }
}
