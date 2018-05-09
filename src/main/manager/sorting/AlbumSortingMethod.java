package manager.sorting;

import model.album.Album;
import model.album.AlbumSortingCriterias;

import java.util.List;

public abstract class AlbumSortingMethod
{
    public abstract List<Album> sort();

    public AlbumSortingCriterias getAlbumSortingCriteria()
    {
        return albumSortingCriteria;
    }

    protected AlbumSortingCriterias albumSortingCriteria;
}
