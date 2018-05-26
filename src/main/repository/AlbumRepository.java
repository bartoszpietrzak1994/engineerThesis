package repository;

import model.album.Album;
import model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AlbumRepository extends JpaRepository<Album, Long>
{
    List<Album> findAllByUser(User user);

    @Query("SELECT a FROM Album a ORDER BY artist")
    List<Album> findAllOrderByArtist();

    @Query("SELECT a FROM Album a ORDER BY title")
    List<Album> findAllOrderByTitle();

    @Query("SELECT a FROM Album a ORDER BY albumRating")
    List<Album> findAllOrderByRating();

    @Query("SELECT a FROM Album a ORDER BY ratingDate")
    List<Album> findAllOrderByRatingDate();

    @Query("SELECT a FROM Album a ORDER BY releaseDate")
    List<Album> findAllOrderByReleaseDate();
}
