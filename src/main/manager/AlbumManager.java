package manager;

import model.album.Album;
import model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.AlbumRepository;
import request.album.AddAlbumRequest;
import request.album.FindAllUserAlbumRequest;
import request.album.RateAlbumRequest;
import response.album.AddAlbumResponse;
import response.album.FindAllUserAlbumsResponse;
import response.album.RateAlbumResponse;
import service.AuthenticationService;

import java.sql.Date;
import java.util.List;

@Component
final public class AlbumManager
{
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AuthenticationService authenticationService;

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
        album.setReleaseDate(Date.valueOf(addAlbumRequest.getReleaseDate()));

        User user = authenticationService.findUserByUsername(addAlbumRequest.getUserName());
        album.setUser(user);

        AddAlbumResponse addAlbumResponse = new AddAlbumResponse();

        try
        {
            albumRepository.save(album);
        }
        catch (Exception e)
        {
            e.printStackTrace();
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

    public FindAllUserAlbumsResponse findAllUserAlbums(FindAllUserAlbumRequest findAllUserAlbumRequest)
    {
        String userName = findAllUserAlbumRequest.getUserName();
        User user = authenticationService.findUserByUsername(userName);

        FindAllUserAlbumsResponse response = new FindAllUserAlbumsResponse();
        List<Album> userAlbums;

        try
        {
             userAlbums = albumRepository.findAllByUser(user);
        }
        catch (Exception e)
        {
            response.setSuccessful(false);
            response.setErrorMessage(e.getMessage());
            return response;
        }

        response.setSuccessful(true);
        response.setAlbumList(userAlbums);

        return response;
    }

    private Date getCurrentDate()
    {
        return new Date(new java.util.Date().getTime());
    }
}
