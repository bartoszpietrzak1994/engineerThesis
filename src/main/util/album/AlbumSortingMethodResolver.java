package util.album;

import manager.sorting.AlbumSortingMethod;
import model.album.AlbumSortingCriterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
final public class AlbumSortingMethodResolver
{
    @Autowired
    private List<AlbumSortingMethod> albumSortingMethods;

    public AlbumSortingMethod resolve(AlbumSortingCriterias sortingCriteria)
    {
         return albumSortingMethods.stream().filter(sortingMethod -> sortingMethod
                .getAlbumSortingCriteria().equals(sortingCriteria)).findFirst().get();
    }
}
