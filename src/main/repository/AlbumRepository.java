package repository;

import model.album.Album;
import model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long>
{
    List<Album> findAllByUser(User user);

    @Query("SELECT a FROM Album a GROUP BY artist")
    List<Album> findAllGroupByArtist();

    @Query("SELECT a FROM Album a GROUP BY title")
    List<Album> findAllGroupByTitle();

    @Query("SELECT a FROM Album a GROUP BY rating")
    List<Album> findAllGroupByRating();

    @Query("SELECT a FROM Album a GROUP BY ratingDate")
    List<Album> findAllGroupByRatingDate();

    @Query("SELECT a FROM Album a GROUP BY releaseDate")
    List<Album> findAllGroupByReleaseDate();
}
