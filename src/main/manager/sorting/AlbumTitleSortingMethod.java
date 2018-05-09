package manager.sorting;

import model.album.Album;
import model.album.AlbumOrderingCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.AlbumRepository;

import java.util.List;

@Component
final public class AlbumTitleSortingMethod extends AlbumSortingMethod
{
    @Autowired
    private AlbumRepository albumRepository;

    public AlbumTitleSortingMethod()
    {
        this.albumSortingCriteria = AlbumOrderingCriteria.ALBUM_TITLE;
    }

    @Override
    public List<Album> sort()
    {
        return albumRepository.findAllOrderByTitle();
    }
}
