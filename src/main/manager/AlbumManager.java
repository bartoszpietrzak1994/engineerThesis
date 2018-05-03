package manager;

import model.album.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.AlbumRepository;
import request.album.AddAlbumRequest;
import request.album.RateAlbumRequest;
import response.album.AddAlbumResponse;
import response.album.RateAlbumResponse;

import java.sql.Date;

@Component
final public class AlbumManager
{
    @Autowired
    private AlbumRepository albumRepository;

    public AddAlbumResponse addAlbum(AddAlbumRequest addAlbumRequest)
    {
        Album album = new Album();

        album.setArtist(addAlbumRequest.getArtist());
        album.setTitle(addAlbumRequest.getTitle());

        if (addAlbumRequest.getAlbumRating() != null)
        {
            album.setRatingDate(getCurrentDate());
            album.setAlbumRating(addAlbumRequest.getAlbumRating());
        }

        album.setAlbumCover(addAlbumRequest.getAlbumCover());
        album.setReleaseDate(addAlbumRequest.getReleaseDate());
        album.setUser(addAlbumRequest.getUser());

        AddAlbumResponse addAlbumResponse = new AddAlbumResponse();

        try
        {
            albumRepository.save(album);
        }
        catch (Exception e)
        {
            addAlbumResponse.setSuccessful(false);
            addAlbumResponse.setErrorMessage(e.getMessage());

            return addAlbumResponse;
        }

        addAlbumResponse.setSuccessful(true);

        return addAlbumResponse;
    }

    public RateAlbumResponse rateAlbum(RateAlbumRequest rateAlbumRequest)
    {
        Album album = albumRepository.getOne(rateAlbumRequest.getAlbumId());

        album.setAlbumRating(rateAlbumRequest.getAlbumRating());
        album.setRatingDate(getCurrentDate());

        RateAlbumResponse rateAlbumResponse = new RateAlbumResponse();

        try
        {
            albumRepository.save(album);
        }
        catch (Exception e)
        {
            rateAlbumResponse.setSuccessful(false);
            rateAlbumResponse.setErrorMessage(e.getMessage());

            return rateAlbumResponse;
        }

        rateAlbumResponse.setSuccessful(true);

        return rateAlbumResponse;
    }

    private Date getCurrentDate()
    {
        return new Date(new java.util.Date().getTime());
    }
}
