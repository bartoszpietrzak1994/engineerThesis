package manager.sorting;

import model.album.Album;
import model.album.AlbumOrderingCriteria;

import java.util.List;

public abstract class AlbumSortingMethod
{
    public abstract List<Album> sort();

    public AlbumOrderingCriteria getAlbumSortingCriteria()
    {
        return albumSortingCriteria;
    }

    protected AlbumOrderingCriteria albumSortingCriteria;
}
