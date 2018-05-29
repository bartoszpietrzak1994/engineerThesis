package util.album;

import manager.sorting.AlbumSortingMethod;
import model.album.AlbumOrderingCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlbumSortingMethodResolver
{
    @Autowired
    private List<AlbumSortingMethod> albumSortingMethods;

    public AlbumSortingMethod resolve(AlbumOrderingCriteria sortingCriteria)
    {
         return albumSortingMethods.stream().filter(sortingMethod -> sortingMethod
                .getAlbumSortingCriteria().equals(sortingCriteria)).findFirst().get();
    }
}
